package myRestaurant.converter;

import myRestaurant.dto.DishDTO;
import myRestaurant.dto.OrderDTO;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.OrderEntity;
import myRestaurant.utils.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderDTO toOrderDTO(OrderEntity orderEntity){
        List<DishDTO> dishDTOS = orderEntity.getDishes().stream()
                .map(DishConverter::toDishDTO)
                .collect(Collectors.toList());
        return OrderDTO.builder()
                .id(orderEntity.getId())
                .number(orderEntity.getNumber())
                .timeOfCreation(orderEntity.getTimeOfCreation())
                .dishes(dishDTOS)
                .waiterId(orderEntity.getWaiterId())
                .orderStatus(OrderStatus.valueOf(orderEntity.getOrderStatus()))
                .build();
    }
}
