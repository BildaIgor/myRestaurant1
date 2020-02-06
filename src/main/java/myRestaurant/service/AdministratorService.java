package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.converter.CookConverter;
import myRestaurant.converter.WaiterConverter;
import myRestaurant.dto.CookDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.dto.WaiterDto;
import myRestaurant.entity.Dish;
import myRestaurant.entity.Order;
import myRestaurant.repository.*;
import myRestaurant.utils.OrderStatus;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdministratorService {
    private final WaiterRepository waiterRepository;
    private final CookRepository cookRepository;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public void createWaiter(WaiterDto waiterDto){
        waiterRepository.save(WaiterConverter.toWaiterEntity(waiterDto));
    }

    public void createCook(CookDto cookDto){
        cookRepository.save(CookConverter.toCookEntity(cookDto));
    }

    public void deleteWaiter(Integer waiterId){
        waiterRepository.delete(waiterRepository.getById(waiterId));
    }

    public void deleteCook(Integer cookId){
        cookRepository.delete(cookRepository.getById(cookId));
    }

    public void addDishInPlayList(Integer dishId){
        Dish dish = dishRepository.getById(dishId);
        dish.setQuantity(500);
        dishRepository.save(dish);
    }
    public void addDishInStopList(Integer dishId, Integer balance){
        Dish dish = dishRepository.getById(dishId);
        dish.setQuantity(balance);
        dishRepository.save(dish);
    }
    public void setNormalQuantity(Integer dishId){
        Dish dish = dishRepository.getById(dishId);
        dish.setQuantity(100);
        dishRepository.save(dish);
    }
    public void makeDiscount(Integer orderId, Double discount){
        Order order = orderRepository.getById(orderId);
        if(order.getOrderStatus().equals(OrderStatus.OPENED.getTitle())) {
            order.setDiscount(discount);
            order.setCheckAmount(order.getCheckAmount() - order.getCheckAmount() * (discount / 100));
            orderRepository.save(order);
        } else throw new IllegalArgumentException("Order is closed");
    }
    public void deleteDiscount(Integer orderId){
        Order order = orderRepository.getById(orderId);
        if(order.getOrderStatus().equals(OrderStatus.OPENED.getTitle())) {
            order.setCheckAmount(order.getCheckAmount() / (1.0 - (order.getDiscount() / 100)));
            order.setDiscount(1);
            orderRepository.save(order);
        } else throw new IllegalArgumentException("Order is closed");
    }
    public void closeDay(){

        List<Order> orderEntities = orderRepository.getAllByOrderStatus(OrderStatus.PAID.getTitle());
        orderRepository.saveAll(orderEntities.stream()
                .peek(x -> x.setOrderStatus(OrderStatus.READY_FOR_REPORTED.getTitle()))
                .collect(Collectors.toList()));
    }

    public Map<String, Double> getCashbox() {
        Map<String, Double> map = new HashMap<>();
        map.put("Cash from OPENED orders", orderService.getOrdersByStatus(OrderStatus.OPENED).stream()
                .map(OrderDto::getCheckAmount)
                .reduce(0.0, Double::sum)
        );
        map.put("Cash from CLOSED orders", orderService.getOrdersByStatus(OrderStatus.CLOSED).stream()
                .map(OrderDto::getCheckAmount)
                .reduce(0.0, Double::sum)
        );
        map.put("Cash from PAID orders", orderService.getOrdersByStatus(OrderStatus.PAID).stream()
                .map(OrderDto::getCheckAmount)
                .reduce(0.0, Double::sum)
        );
        return map;

    }
}
