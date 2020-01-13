package myRestaurant.converter;

import myRestaurant.dto.DishDto;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.MenuEntity;
import myRestaurant.entity.OrderDishesEntity;
import myRestaurant.utils.DishStatus;

public class DishConverter {
    public static DishDto toDishDTO(DishEntity dishEntity, DishStatus dishStatus){
        return DishDto.builder()
                .id(dishEntity.getId())
                .category(dishEntity.getCategory())
                .name(dishEntity.getName())
                .price(dishEntity.getPrice())
                .dishStatus(dishStatus)
                .build();
    }
//    public static DishEntity toDishEntity(MenuEntity menuEntity, DishStatus dishStatus){
//        return DishEntity.builder()
//                .menuId(menuEntity.getId())
//                .category(menuEntity.getCategory())
//                .name(menuEntity.getName())
//                .price(menuEntity.getPrice())
//                .dishStatus(dishStatus.getTitle())
//                .build();
//    }
}
