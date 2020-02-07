package myRestaurant.converter;

import myRestaurant.dto.OrderDto;
import myRestaurant.dto.WaiterDto;
import myRestaurant.entity.Waiter;

import java.util.List;
import java.util.stream.Collectors;

public class WaiterConverter {
    public static WaiterDto toWaiterDto(Waiter waiter){
        List<OrderDto> orderDtos = waiter.getOrders().stream()
                .map(OrderConverter :: toOrderDTO)
                .collect(Collectors.toList());
        return WaiterDto.builder()
                .id(waiter.getId())
                .name(waiter.getName())
                .orders(orderDtos)
                .build();
    }
    public static Waiter toWaiterEntity(WaiterDto waiterDto){
        return Waiter.builder()
                .name(waiterDto.getName())
                .build();
    }
}
