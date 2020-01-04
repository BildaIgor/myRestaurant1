package myRestaurant.converter;

import myRestaurant.dto.DishDTO;
import myRestaurant.dto.OrderDTO;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.OrderEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {
    public static OrderDTO toOrderDTO(OrderEntity orderEntity){
        List<DishDTO> dishDTOS = new ArrayList<>();
        for (DishEntity x:orderEntity.getDishes()
             ) {
            dishDTOS.add(DishDTO.builder()
            .id(x.getId())
            .category(x.getCategory())
            .name(x.getName())
            .build());
        }
        return OrderDTO.builder()
                .id(orderEntity.getId())
                .number(orderEntity.getNumber())
                .timeOfCreation(orderEntity.getTimeOfCreation())
                .dishes(dishDTOS)
                .waiterId(orderEntity.getWaiterId())
                .build();
    }
}
