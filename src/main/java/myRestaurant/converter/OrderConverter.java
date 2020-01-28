package myRestaurant.converter;

import myRestaurant.dto.DishDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.entity.OrderDishes;
import myRestaurant.entity.Order;
import myRestaurant.utils.DishStatus;
import myRestaurant.utils.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {
    public static OrderDto toOrderDTO(Order order, List<OrderDishes> orderDishesEntities){
        List<DishDto> dishDtos = new ArrayList<>();
        for (int i = 0; i < order.getDishes().size() ; i++) {
            dishDtos.add(DishDto.builder()
                    .id(order.getDishes().get(i).getId())
                    .orderDishId(orderDishesEntities.get(i).getId())
                    .category(order.getDishes().get(i).getCategory())
                    .name(order.getDishes().get(i).getName())
                    .price(order.getDishes().get(i).getPrice())
                    .dishStatus(DishStatus.valueOf(orderDishesEntities.get(i).getDishStatus()))
                    .build()
            );
        }
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
