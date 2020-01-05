package myRestaurant.converter;

import myRestaurant.dto.DishDto;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.MenuEntity;
import myRestaurant.utils.DishStatus;

public class DishConverter {
    public static DishDto toDishDTO(DishEntity dishEntity){
        return DishDto.builder()
                .id(dishEntity.getMenuId())
                .category(dishEntity.getCategory())
                .name(dishEntity.getName())
                .price(dishEntity.getPrice())
                .dishStatus(DishStatus.valueOf(dishEntity.getDishStatus()))
                .build();
    }
    public static DishEntity toDishEntity(MenuEntity menuEntity, DishStatus dishStatus){
        return DishEntity.builder()
                .menuId(menuEntity.getId())
                .category(menuEntity.getCategory())
                .name(menuEntity.getName())
                .price(menuEntity.getPrice())
                .dishStatus(dishStatus.getTitle())
                .build();
    }
}
