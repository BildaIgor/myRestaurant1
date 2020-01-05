package myRestaurant.converter;

import myRestaurant.dto.MenuDto;
import myRestaurant.entity.MenuEntity;

public class MenuConverter {
    public static MenuDto toMenuDto(MenuEntity menuEntity){
        return MenuDto.builder()
                .id(menuEntity.getId())
                .category(menuEntity.getCategory())
                .name(menuEntity.getName())
                .price(menuEntity.getPrice())
                .build();
    }
}
