package myRestaurant.converter;

import myRestaurant.dto.MenuDto;
import myRestaurant.entity.DishEntity;

public class MenuConverter {
    public static MenuDto toMenuDto(DishEntity dishEntity){
        return MenuDto.builder()
                .id(dishEntity.getId())
                .category(dishEntity.getCategory())
                .name(dishEntity.getName())
                .price(dishEntity.getPrice())
                .build();
    }
}
