package myRestaurant.converter;

import myRestaurant.dto.DishDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.OrderDishesEntity;
import myRestaurant.entity.OrderEntity;
import myRestaurant.utils.DishStatus;
import myRestaurant.utils.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderDto toOrderDTO(OrderEntity orderEntity, List<OrderDishesEntity> orderDishesEntities, List<DishEntity> dishEntities){
        List<DishDto> dishDtos = new ArrayList<>();
        for (int i = 0; i <orderDishesEntities.size() ; i++) {
            dishDtos.add(DishConverter.toDishDTO(dishEntities.get(i), DishStatus.valueOf(orderDishesEntities.get(i).getDishStatus())));
        }
        return OrderDto.builder()
                .id(orderEntity.getId())
                .number(orderEntity.getNumber())
                .timeOfCreation(orderEntity.getTimeOfCreation())
                .dishes(dishDtos)
                .waiterId(orderEntity.getWaiterId())
                .orderStatus(OrderStatus.valueOf(orderEntity.getOrderStatus()))
                .checkAmount(orderEntity.getCheckAmount())
                .build();
    }
}
