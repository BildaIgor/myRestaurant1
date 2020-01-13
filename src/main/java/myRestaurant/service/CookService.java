package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.converter.DishConverter;
import myRestaurant.dto.DishDto;
import myRestaurant.entity.CookEntity;
import myRestaurant.entity.DishEntity;
import myRestaurant.repository.CookRepository;
import myRestaurant.repository.DishRepository;
import myRestaurant.utils.DishStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CookService {
//    private final DishRepository dishRepository;
//    private final CookRepository cookRepository;

//    public List<DishDto> getNewDishes(){
//        List<DishEntity> dishEntities = dishRepository.getAllByDishStatus(DishStatus.NEW.getTitle());
//        return dishEntities.stream()
//                .map(DishConverter :: toDishDTO)
//                .collect(Collectors.toList());
//    }
//    public void startCooking(Integer dishId){
//        DishEntity dishEntity = dishRepository.getById(dishId);
//        dishEntity.setDishStatus(DishStatus.IN_COOKING.getTitle());
//        dishRepository.save(dishEntity);
//    }
//    public void endCooking(Integer dishId){
//        DishEntity dishEntity = dishRepository.getById(dishId);
//        dishEntity.setDishStatus(DishStatus.COOKED.getTitle());
//        dishRepository.save(dishEntity);
//    }
//    public void setCookingTime(Integer cookId, Integer cookingTime){
//        CookEntity cookEntity = cookRepository.getById(cookId);
//        cookEntity.setCookingTime(cookingTime);
//        cookRepository.save(cookEntity);
//
//    }
    

}
