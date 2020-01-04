package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myRestaurant.converter.DishConverter;
import myRestaurant.converter.OrderConverter;
import myRestaurant.converter.WaiterConverter;
import myRestaurant.dto.DishDTO;
import myRestaurant.dto.OrderDTO;
import myRestaurant.dto.WaiterDTO;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.OrderEntity;
import myRestaurant.entity.WaiterEntity;
import myRestaurant.repository.MenuRepository;
import myRestaurant.repository.OrderRepository;
import myRestaurant.repository.WaiterRepository;
import myRestaurant.utils.DishStatus;
import myRestaurant.utils.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaiterService {
    private final WaiterRepository waiterRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public void createOrder(Integer waiterId, Integer number){
        OrderEntity orderEntity = OrderEntity.builder()
                .number(number)
                .timeOfCreation(new Date())
                .dishes(new ArrayList<>())
                .waiterId(waiterId)
                .orderStatus(OrderStatus.NEW.getTitle())
                .build();
        orderRepository.save(orderEntity);
    }
    public void addDishesToOrder(Integer waiterId, Integer orderId, Integer [] dishesId){
      OrderEntity orderEntity = orderRepository.getById(orderId);
      Arrays.stream(dishesId).forEach(dishId->orderEntity.getDishes().add(DishConverter.toDishEntity(menuRepository.getById(dishId), DishStatus.NEW)));
      orderRepository.save(orderEntity);
    }
    public List<OrderDTO> getOrders(Integer waiterId, Integer orderId){
        List<OrderDTO> orderDTOS = new ArrayList<>();
       if(orderId != null){
           orderDTOS.add(OrderConverter.toOrderDTO(orderRepository.getById(orderId)));
           return orderDTOS;
       } else {
           List<OrderEntity> orderEntity = orderRepository.getAllByWaiterId(waiterId);
           for (OrderEntity x :orderEntity
                ) {
               orderDTOS.add(OrderConverter.toOrderDTO(x));
           }
           return orderDTOS;
       }
    }
}
