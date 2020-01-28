package myRestaurant.converter;

import myRestaurant.dto.CookDto;
import myRestaurant.entity.Cook;

public class CookConverter {
    public static Cook toCookEntity(CookDto cookDto){
        return Cook.builder()
                .name(cookDto.getName())
                .position(cookDto.getPosition())
                .cookingTime(cookDto.getCookingTime())
                .build();
    }

}
