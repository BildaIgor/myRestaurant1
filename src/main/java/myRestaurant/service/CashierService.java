package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.dto.ReportDto;
import myRestaurant.entity.Order;
import myRestaurant.repository.OrderRepository;
import myRestaurant.utils.OrderStatus;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CashierService {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final WaiterService waiterService;

    public void payOrder(Integer orderId) {
        Order order = orderRepository.getById(orderId);
        if (order.getOrderStatus().equals(OrderStatus.CLOSED.getTitle())) {
            order.setOrderStatus(OrderStatus.PAID.getTitle());
            order.setTimeOfPaid(new Date());
            orderRepository.save(order);
        } else
            throw new IllegalArgumentException(String.format("Order with number: %s does not closed!", order.getNumber()));

    }

    public ReportDto getReport(){
        List<Order> orderEntities =  orderRepository.getAllByOrderStatus(OrderStatus.READY_FOR_REPORTED.getTitle());
        Optional<Date> from = orderEntities.stream()
                .map(Order::getTimeOfCreation)
                .min(Date::compareTo);
        Optional<Date> to = orderEntities.stream()
                .map(Order::getTimeOfPaid)
                .max(Date::compareTo);
        Optional<Double> sum = orderEntities.stream()
                .map(Order::getCheckAmount)
                .reduce(Double :: sum);
        Optional<Double> sumOfPercentage = orderEntities.stream()
                .map(x->orderService.getPercentageOfSalesByOrder(x.getId()))
                .reduce(Double :: sum);

        orderRepository.saveAll(orderEntities.stream()
        .peek(x->x.setOrderStatus(OrderStatus.REPORTED.getTitle()))
        .collect(Collectors.toList()));


        return ReportDto.builder()
                .id(1)
                .from(from.get())
                .to(to.get())
                .cashierName("VASIA")
                .paidOrders(orderService.getOrdersByStatus(null,null,OrderStatus.READY_FOR_REPORTED))
                .paidDishes(waiterService.getSalesDishesStatistic(null))
                .sumCash(sum.get())
                .sumPercentageOfSales(sumOfPercentage.get())
                .build();
    }
}
