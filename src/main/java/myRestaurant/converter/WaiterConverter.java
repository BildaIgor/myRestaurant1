package myRestaurant.converter;

import myRestaurant.dto.WaiterDTO;
import myRestaurant.entity.OrderEntity;
import myRestaurant.entity.WaiterEntity;

import java.util.List;

public class WaiterConverter {
    public static WaiterDTO toWaiterDto(WaiterEntity waiterEntity){
        return WaiterDTO.builder()
                .id(waiterEntity.getId())
                .name(waiterEntity.getName())
                .build();

    }
}
