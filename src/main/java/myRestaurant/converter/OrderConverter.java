package myRestaurant.converter;

import myRestaurant.dto.DishDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.entity.OrderEntity;
import myRestaurant.utils.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderDto toOrderDTO(OrderEntity orderEntity){
        List<DishDto> dishDtos = orderEntity.getDishes().stream()
                .map(DishConverter::toDishDTO)
                .collect(Collectors.toList());
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
