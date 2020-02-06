
package myRestaurant.converter;

import myRestaurant.dto.DishDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.entity.OrderDishes;
import myRestaurant.entity.Order;
import myRestaurant.utils.DishStatus;
import myRestaurant.utils.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderDto toOrderDTO(Order order){
        List<DishDto> dishDtos = order.getOrderDishes().stream()
                .map(DishConverter::toDto)
                .collect(Collectors.toList());

        return OrderDto.builder()
                .id(order.getId())
                .number(order.getNumber())
                .timeOfCreation(order.getTimeOfCreation())
                .dishes(dishDtos)
                .waiterDto(WaiterConverter.toWaiterDto(order.getWaiter()))
                .orderStatus(OrderStatus.valueOf(order.getOrderStatus()))
                .checkAmount(order.getCheckAmount())
                .discount(order.getDiscount())
                .timeOfPaid(order.getTimeOfPaid())
                .build();
    }
}

