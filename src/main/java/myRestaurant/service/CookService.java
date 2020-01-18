package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.converter.CookConverter;
import myRestaurant.converter.DishConverter;
import myRestaurant.converter.MenuConverter;
import myRestaurant.dto.CookDishDto;
import myRestaurant.dto.CookDto;
import myRestaurant.dto.DishDto;
import myRestaurant.dto.MenuDto;
import myRestaurant.entity.CookEntity;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.OrderDishesEntity;
import myRestaurant.repository.CookRepository;
import myRestaurant.repository.DishRepository;
import myRestaurant.repository.OrderDishesRepository;
import myRestaurant.utils.DishStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CookService {
    private final DishRepository dishRepository;
    private final CookRepository cookRepository;
    private final OrderDishesRepository orderDishesRepository;

    public List<CookDishDto> getNewDishes(){
//        List<DishEntity> dishEntities = ord.getAllByDishStatus(DishStatus.NEW.getTitle());
//        return dishEntities.stream()
//                .map(DishConverter :: toDishDTO)
//                .collect(Collectors.toList());
        List<OrderDishesEntity> orderDishesEntities = orderDishesRepository.getAllByDishStatus(DishStatus.NEW.getTitle());
       List<CookDishDto> cookDishDtos = new ArrayList<>();
       orderDishesEntities.forEach(
               x->{
                   DishEntity dishEntity = dishRepository.getById(x.getDishId());
                   cookDishDtos.add(CookDishDto.builder()
                   .id(x.getId())
                   .category(dishEntity.getCategory())
                   .name(dishEntity.getName())
                   .build());
               }
       );
       return cookDishDtos;
    }
    public void startCooking(Integer id){
       OrderDishesEntity orderDishesEntity = orderDishesRepository.getById(id);
       orderDishesEntity.setDishStatus(DishStatus.IN_COOKING.getTitle());
       orderDishesEntity.setStartCookingTime(new Date());
       orderDishesRepository.save(orderDishesEntity);
    }
    public void endCooking(Integer id){
        OrderDishesEntity orderDishesEntity = orderDishesRepository.getById(id);
        orderDishesEntity.setDishStatus(DishStatus.COOKED.getTitle());
        orderDishesEntity.setAndCookingTime(new Date());
        orderDishesRepository.save(orderDishesEntity);
    }
    public void setCookingTime(Integer cookId, Integer cookingTime){
        CookEntity cookEntity = cookRepository.getById(cookId);
        cookEntity.setCookingTime(cookingTime);
        cookRepository.save(cookEntity);

    }
    

}
