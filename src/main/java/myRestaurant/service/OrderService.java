package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myRestaurant.converter.DishConverter;
import myRestaurant.converter.MenuConverter;
import myRestaurant.converter.OrderConverter;
import myRestaurant.dto.*;
import myRestaurant.entity.DishEntity;

import myRestaurant.entity.OrderDishesEntity;
import myRestaurant.entity.OrderEntity;
import myRestaurant.repository.*;
import myRestaurant.utils.DishStatus;
import myRestaurant.utils.OrderStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final OrderDishesRepository orderDishesRepository;

    public void createOrder(CreateOrderDto createOrderDto){
        OrderEntity orderEntity = OrderEntity.builder()
                .number(createOrderDto.getTableNumber())
                .timeOfCreation(new Date())
                .waiterId(createOrderDto.getWaiterId())
                .orderStatus(OrderStatus.OPENED.getTitle())
                .build();
        orderRepository.save(orderEntity);
    }
    public void addDishesToOrder(AddDishesToOrderDto addDishesToOrderDto){
        addDishesToOrderDto.getDishesId().forEach(dishId->orderDishesRepository.save(
                OrderDishesEntity.builder()
                        .orderId(addDishesToOrderDto.getOrderId())
                        .dishId(dishId)
                        .dishStatus(DishStatus.NEW.getTitle())
                        .addTime(new Date())
                        .build()));
        Integer sum = addDishesToOrderDto.getDishesId().stream()
                .map(x->dishRepository.getById(x).getPrice())
                .reduce(0,Integer::sum);
        OrderEntity orderEntity = orderRepository.getById(addDishesToOrderDto.getOrderId());
        orderEntity.setCheckAmount(orderEntity.getCheckAmount() + sum);
        orderRepository.save(orderEntity);
    }
    public List<OrderDto> getOrders(Integer waiterId, Integer orderId){
        List<OrderDto> orderDtos = new ArrayList<>();
       if(orderId != null){
           OrderEntity orderEntity = orderRepository.getById(orderId);
           List<OrderDishesEntity> orderDishesEntities = orderDishesRepository.getAllByOrderId(orderId);
           List<DishEntity> dishEntities = new ArrayList<>();
           orderDishesEntities.forEach(
                   x->dishEntities.add(dishRepository.getById(x.getDishId()))
           );
           orderDtos.add(OrderConverter.toOrderDTO(orderEntity,orderDishesEntities,dishEntities));
           return orderDtos;
       } else {
          List<OrderEntity> orderEntities = orderRepository.getAllByWaiterId(waiterId);
          orderEntities.forEach(x->
          {
              List<OrderDishesEntity> orderDishesEntities = orderDishesRepository.getAllByOrderId(x.getId());
              List<DishEntity> dishEntities = new ArrayList<>();
              orderDishesEntities.forEach(
                      b->dishEntities.add(dishRepository.getById(b.getDishId()))
              );
              orderDtos.add(OrderConverter.toOrderDTO(x,orderDishesEntities,dishEntities));
          });
          return orderDtos;
       }
    }
    public List<MenuDto> getDishesInMenu(String column,String name){
        if(column.equals("name")){
            List<DishEntity >dishEntities = dishRepository.getAllByNameContaining(name);
            return dishEntities.stream()
                    .map(MenuConverter::toMenuDto)
                    .collect(Collectors.toList());
        } else {
            List<DishEntity >dishEntities = dishRepository.getAllByCategory(name);
            return dishEntities.stream()
                    .map(MenuConverter :: toMenuDto)
                    .collect(Collectors.toList());
        }
    }

    public void removeDishFromOrder(Integer orderId, Integer dishId){
       OrderEntity orderEntity = orderRepository.getById(orderId);
       OrderDishesEntity orderDishesEntity = orderDishesRepository.getByDishIdAndOrderId(dishId , orderId).get(0);
       orderDishesRepository.removeById(orderDishesEntity.getId());
       orderEntity.setCheckAmount(orderEntity.getCheckAmount() - dishRepository.getById(dishId).getPrice());
       orderRepository.save(orderEntity);

    }
}
