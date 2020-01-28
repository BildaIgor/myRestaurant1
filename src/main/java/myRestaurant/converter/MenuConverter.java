package myRestaurant.converter;

import myRestaurant.dto.MenuDto;
import myRestaurant.entity.Dish;

public class MenuConverter {
    public static MenuDto toMenuDto(Dish dish){
        return MenuDto.builder()
                .id(dish.getId())
                .category(dish.getCategory())
                .name(dish.getName())
                .price(dish.getPrice())
                .build();
    }
}
