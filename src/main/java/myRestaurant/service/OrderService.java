package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myRestaurant.converter.DishConverter;
import myRestaurant.converter.MenuConverter;
import myRestaurant.converter.OrderConverter;
import myRestaurant.dto.AddDishesToOrderDto;
import myRestaurant.dto.CreateOrderDto;
import myRestaurant.dto.MenuDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.MenuEntity;
import myRestaurant.entity.OrderEntity;
import myRestaurant.repository.*;
import myRestaurant.utils.DishStatus;
import myRestaurant.utils.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderDishesRepository orderDishesRepository;

    public void createOrder(CreateOrderDto createOrderDto){
        List<DishEntity> dishEntities = new ArrayList<>();
        createOrderDto.getDishesId().forEach(x->dishEntities.add(DishConverter.toDishEntity(menuRepository.getById(x),DishStatus.NEW)));
        Integer sum = dishEntities.stream()
                .map(DishEntity::getPrice)
                .reduce(0, Integer :: sum);
        OrderEntity orderEntity = OrderEntity.builder()
                .number(createOrderDto.getTableNumber())
                .timeOfCreation(new Date())
                .dishes(dishEntities)
                .waiterId(createOrderDto.getWaiterId())
                .orderStatus(OrderStatus.OPENED.getTitle())
                .checkAmount(sum)
                .build();
        orderRepository.save(orderEntity);
    }
    public void addDishesToOrder(AddDishesToOrderDto addDishesToOrderDto){
      OrderEntity orderEntity = orderRepository.getById(addDishesToOrderDto.getOrderId());
      addDishesToOrderDto.getDishesId().forEach(dishId->orderEntity.getDishes().add(DishConverter.toDishEntity(menuRepository.getById(dishId), DishStatus.NEW)));
      Integer sum = orderEntity.getDishes().stream()
              .map(DishEntity::getPrice)
              .reduce(0,Integer :: sum);
      orderEntity.setCheckAmount(sum);
      orderRepository.save(orderEntity);
    }
    public List<OrderDto> getOrders(Integer waiterId, Integer orderId){
        List<OrderDto> orderDtos = new ArrayList<>();
       if(orderId != null){
           orderDtos.add(OrderConverter.toOrderDTO(orderRepository.getById(orderId)));
           return orderDtos;
       } else {
           List<OrderEntity> orderEntity = orderRepository.getAllByWaiterId(waiterId);
           orderEntity.forEach(x-> orderDtos.add(OrderConverter.toOrderDTO(x)));
           return orderDtos;
       }
    }
    public List<MenuDto> getDishesInMenuByCategory(String category){
        List<MenuEntity> menuEntityes = menuRepository.getAllByCategory(category);
        return menuEntityes.stream()
                .map(MenuConverter :: toMenuDto)
                .collect(Collectors.toList());
    }
    public List<MenuDto> getDishesInMenuByName(String name){
        List<MenuEntity> menuEntities = menuRepository.getAllByNameContaining(name);
        return  menuEntities.stream()
                .map(MenuConverter :: toMenuDto)
                .collect(Collectors.toList());

    }
    public void removeDishFromOrder(Integer orderId, Integer dishId){
       OrderEntity orderEntity = orderRepository.getById(orderId);
       orderEntity.setDishes(orderEntity.getDishes().stream()
                .filter(x->x.getId()!=dishId)
                .collect(Collectors.toList()));
       orderEntity.setCheckAmount(orderEntity.getCheckAmount() - dishRepository.getById(dishId).getPrice());
        orderRepository.save(orderEntity);
        DishEntity dishEntity = dishRepository.getById(dishId);
        dishEntity.setDishStatus("DELETED");
        dishRepository.save(dishEntity);

    }
}
