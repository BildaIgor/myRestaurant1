package myRestaurant.converter;

import myRestaurant.dto.CookDto;
import myRestaurant.entity.CookEntity;

public class CookConverter {
    public static CookEntity toCookEntity(CookDto cookDto){
        return CookEntity.builder()
                .name(cookDto.getName())
                .position(cookDto.getPosition())
                .cookingTime(cookDto.getCookingTime())
                .build();
    }
}
