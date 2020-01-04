package myRestaurant.converter;

import myRestaurant.dto.DishDTO;
import myRestaurant.entity.DishEntity;

public class DishConverter {
    public static DishEntity toDishEntity(DishDTO dishDTO){
        return DishEntity.builder()
                .menuId(dishDTO.getId())
                .category(dishDTO.getCategory())
                .name(dishDTO.getName())
                .build();

    }
}
