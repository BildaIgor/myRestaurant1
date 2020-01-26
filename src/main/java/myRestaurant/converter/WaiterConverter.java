package myRestaurant.converter;

import myRestaurant.dto.WaiterDto;
import myRestaurant.entity.WaiterEntity;

public class WaiterConverter {
    public static WaiterDto toWaiterDto(WaiterEntity waiterEntity){
        return WaiterDto.builder()
                .id(waiterEntity.getId())
                .name(waiterEntity.getName())

                .build();
    }
    public static WaiterEntity toWaiterEntity(WaiterDto waiterDto){
        return WaiterEntity.builder()
                .name(waiterDto.getName())

                .build();
    }
}
