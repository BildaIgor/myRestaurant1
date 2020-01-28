package myRestaurant.converter;

import myRestaurant.dto.WaiterDto;
import myRestaurant.entity.Waiter;

public class WaiterConverter {
    public static WaiterDto toWaiterDto(Waiter waiter){
        return WaiterDto.builder()
                .id(waiter.getId())
                .name(waiter.getName())
                .build();
    }
    public static Waiter toWaiterEntity(WaiterDto waiterDto){
        return Waiter.builder()
                .name(waiterDto.getName())
                .build();
    }
}
