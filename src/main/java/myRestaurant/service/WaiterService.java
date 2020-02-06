package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.converter.DishConverter;
import myRestaurant.converter.MenuConverter;
import myRestaurant.dto.MenuDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.dto.WaiterDto;
import myRestaurant.entity.Order;
import myRestaurant.entity.OrderDishes;
import myRestaurant.entity.Waiter;
import myRestaurant.repository.DishRepository;
import myRestaurant.repository.OrderRepository;
import myRestaurant.repository.WaiterRepository;
import myRestaurant.utils.OrderStatus;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;


@Service
@RequiredArgsConstructor
public class WaiterService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final WaiterRepository waiterRepository;

    public Double getPercentageOfSalesByOrder(Integer waiterId, Integer orderId) {
        if (orderId == null) {
            List<Order> orderEntities = orderRepository.getAllByWaiterIdAndOrderStatus(waiterId, OrderStatus.REPORTED.getTitle());
            return orderEntities.stream()
                    .map(x -> orderService.getPercentageOfSalesByOrder(x.getId()))
                    .reduce(0.0, Double::sum);
        } else {
            return orderService.getPercentageOfSalesByOrder(orderId);
        }
    }

    public Double getPercentageOfSalesByTime(Integer waiterId, String dateFrom, String dateTo) {
        String[] from = dateFrom.split("-");
        String[] to = dateTo.split("-");
        Date firstDate = new Date(Integer.parseInt(from[0]) - 1900, Integer.parseInt(from[1]) - 1, Integer.parseInt(from[2]));
        Date secondDate = new Date(Integer.parseInt(to[0]) - 1900, Integer.parseInt(to[1]) - 1, Integer.parseInt(to[2]));
        List<Order> orderEntities = orderRepository.getAllByTimeOfCreationBetweenAndWaiterId(firstDate, secondDate, waiterId);
        return orderEntities.stream()
                .map(x -> orderService.getPercentageOfSalesByOrder(x.getId()))
                .reduce(0.0, Double::sum);
    }

    public List<OrderDto> getPaidOrders(Integer waiterId) {
        return orderService.getOrdersByStatus(OrderStatus.PAID).stream()
                .filter(order->order.getWaiterDto().getId() == waiterId)
                .collect(Collectors.toList());
    }

    public Map<MenuDto, Long> getSalesDishesStatistic(Integer waiterId) {
        List<Order> orderEntities;
        if (waiterId != null) {
            orderEntities = orderRepository.getAllByWaiterIdAndOrderStatus(waiterId, OrderStatus.REPORTED.getTitle());
        } else {
            orderEntities = orderRepository.getAllByOrderStatus(OrderStatus.REPORTED.getTitle());
        }
        return orderEntities.stream()
                .flatMap(orderEntity -> orderEntity.getOrderDishes().stream())
                .map(OrderDishes::getDish)
                .map(MenuConverter :: toMenuDto)
                .collect(groupingBy(Function.identity(), counting()));
    }

    public List<WaiterDto> getWaiter(Integer waiterId, OrderStatus orderStatus) {
        List<Waiter> waiterEntities = new ArrayList<>();
        if (waiterId == null) {
            waiterEntities = waiterRepository.findAll();
        } else {
            waiterEntities.add(waiterRepository.getById(waiterId));
        }
        return waiterEntities.stream().map(waiter ->
                WaiterDto.builder()
                        .id(waiter.getId())
                        .name(waiter.getName())
                        .orders(orderService.getOrdersByStatus(orderStatus))
                        .build())
                .collect(Collectors.toList());
    }

    public List<MenuDto> getStopList() {
        return dishRepository.getAllByQuantityLessThan(10).stream()
                .map(MenuConverter::toMenuDto)
                .collect(Collectors.toList());

    }

    public List<MenuDto> getPlayList() {
        return dishRepository.getAllByQuantity(500).stream()
                .map(MenuConverter::toMenuDto)
                .collect(Collectors.toList());
    }

    public List<MenuDto> getNormalList() {
        return dishRepository.getAllByQuantity(100).stream()
                .map(MenuConverter::toMenuDto)
                .collect(Collectors.toList());

    }


}
