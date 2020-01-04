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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.awt.*;
import java.util.ArrayList;
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
                .build();
        orderRepository.save(orderEntity);
    }
    public void addDishToOrder(Integer waiterId, Integer orderId, DishDTO dishDTO){
      OrderEntity orderEntity = orderRepository.getById(orderId);
      orderEntity.getDishes().add(DishConverter.toDishEntity(dishDTO));
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
