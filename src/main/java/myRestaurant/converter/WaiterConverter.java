package myRestaurant.converter;

import myRestaurant.dto.WaiterDto;
import myRestaurant.entity.WaiterEntity;

public class WaiterConverter {
    public static WaiterDto toWaiterDto(WaiterEntity waiterEntity){
        return WaiterDto.builder()
                .name(waiterEntity.getName())
                .percentageOfSales(waiterEntity.getPercentageOfSales())
                .build();
    }
    public static WaiterEntity toWaiterEntity(WaiterDto waiterDto){
        return WaiterEntity.builder()
                .name(waiterDto.getName())
                .percentageOfSales(waiterDto.getPercentageOfSales())
                .build();
    }
}
