package myRestaurant.converter;

import myRestaurant.dto.DishDto;
import myRestaurant.entity.Dish;
import myRestaurant.entity.OrderDishes;
import myRestaurant.utils.DishStatus;

public class DishConverter {
    public static DishDto toDto(OrderDishes orderDishes){
        return DishDto.builder()
                .orderDishId(orderDishes.getId())
                .id(orderDishes.getDish().getId())
                .category(orderDishes.getDish().getCategory())
                .name(orderDishes.getDish().getName())
                .price(orderDishes.getDish().getPrice())
                .dishStatus(DishStatus.valueOf(orderDishes.getDishStatus()))
                .build();
    }
}
