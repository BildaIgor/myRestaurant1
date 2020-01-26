package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.converter.CookConverter;
import myRestaurant.converter.WaiterConverter;
import myRestaurant.dto.CookDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.dto.WaiterDto;
import myRestaurant.entity.CookEntity;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.OrderEntity;
import myRestaurant.repository.*;
import myRestaurant.utils.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

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
        DishEntity dishEntity = dishRepository.getById(dishId);
        dishEntity.setQuantity(500);
        dishRepository.save(dishEntity);
    }
    public void addDishInStopList(Integer dishId, Integer balance){
        DishEntity dishEntity = dishRepository.getById(dishId);
        dishEntity.setQuantity(balance);
        dishRepository.save(dishEntity);
    }
    public void setNormalQuantity(Integer dishId){
        DishEntity dishEntity = dishRepository.getById(dishId);
        dishEntity.setQuantity(100);
        dishRepository.save(dishEntity);
    }
    public void makeDiscount(Integer orderId, Double discount){
        OrderEntity orderEntity = orderRepository.getById(orderId);
        if(orderEntity.getOrderStatus().equals(OrderStatus.OPENED.getTitle())) {
            orderEntity.setDiscount(discount);
            orderEntity.setCheckAmount(orderEntity.getCheckAmount() - orderEntity.getCheckAmount() * (discount / 100));
            orderRepository.save(orderEntity);
        } else throw new IllegalArgumentException("Order is closed");
    }
    public void deleteDiscount(Integer orderId){
        OrderEntity orderEntity = orderRepository.getById(orderId);
        if(orderEntity.getOrderStatus().equals(OrderStatus.OPENED.getTitle())) {
            orderEntity.setCheckAmount(orderEntity.getCheckAmount() / (1.0 - (orderEntity.getDiscount() / 100)));
            orderEntity.setDiscount(1);
            orderRepository.save(orderEntity);
        } else throw new IllegalArgumentException("Order is closed");
    }
    public void closeDay(){

        List<OrderEntity> orderEntities = orderRepository.getAllByOrderStatus(OrderStatus.PAID.getTitle());
        orderRepository.saveAll(orderEntities.stream()
                .peek(x -> x.setOrderStatus(OrderStatus.READY_FOR_REPORTED.getTitle()))
                .collect(Collectors.toList()));
    }

    public Map<String, Double> getCashbox() {
        Map<String, Double> map = new HashMap<>();
        map.put("Cash from OPENED orders", orderService.getOrdersByStatus(null, null, OrderStatus.OPENED).stream()
                .map(OrderDto::getCheckAmount)
                .reduce(0.0, Double::sum)
        );
        map.put("Cash from CLOSED orders", orderService.getOrdersByStatus(null, null, OrderStatus.CLOSED).stream()
                .map(OrderDto::getCheckAmount)
                .reduce(0.0, Double::sum)
        );
        map.put("Cash from PAID orders", orderService.getOrdersByStatus(null, null, OrderStatus.PAID).stream()
                .map(OrderDto::getCheckAmount)
                .reduce(0.0, Double::sum)
        );
        return map;

    }
}
