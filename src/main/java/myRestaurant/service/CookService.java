package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.converter.CookConverter;
import myRestaurant.converter.DishConverter;
import myRestaurant.converter.MenuConverter;

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

    public List<DishDto> getDishesByCategoryAndDishStatus(String category, DishStatus dishStatus) {
        List<OrderDishesEntity> orderDishesEntities = orderDishesRepository.getAllByDishStatusAndDishStatus(DishStatus.NEW.getTitle(), DishStatus.IN_COOKING.getTitle());
        List<DishDto> dishDtos = new ArrayList<>();
        orderDishesEntities.forEach(
                x -> {
                    DishEntity dishEntity = dishRepository.getById(x.getDishId());
                    dishDtos.add(DishDto.builder()
                            .id(dishEntity.getId())
                            .orderDishId(x.getId())
                            .category(dishEntity.getCategory())
                            .name(dishEntity.getName())
                            .price(dishEntity.getPrice())
                            .dishStatus(DishStatus.valueOf(x.getDishStatus()))
                            .build());
                }
        );
        if (category != null) {
            return dishDtos.stream()
                    .filter(x -> x.getCategory().equals(category))
                    .filter(x -> x.getDishStatus().equals(dishStatus))
                    .collect(Collectors.toList());
        } else {
            return dishDtos.stream()
                    .filter(x -> x.getDishStatus().equals(dishStatus))
                    .collect(Collectors.toList());
        }
    }

    public void startCooking(Integer orderDishId) {
        OrderDishesEntity orderDishesEntity = orderDishesRepository.getById(orderDishId);
        if(orderDishesEntity.getDishStatus().equals(DishStatus.NEW.getTitle())) {
            orderDishesEntity.setDishStatus(DishStatus.IN_COOKING.getTitle());
            orderDishesEntity.setStartCookingTime(new Date());
            orderDishesRepository.save(orderDishesEntity);
        } else throw new IllegalArgumentException(String.format("Dish with orderDishId: %s not a new!",orderDishesEntity.getId()));
    }

    public void endCooking(Integer orderDishId) {
        OrderDishesEntity orderDishesEntity = orderDishesRepository.getById(orderDishId);
        if(orderDishesEntity.getDishStatus().equals(DishStatus.COOKED.getTitle())) {
            orderDishesEntity.setDishStatus(DishStatus.COOKED.getTitle());
            orderDishesEntity.setAndCookingTime(new Date());
            orderDishesRepository.save(orderDishesEntity);
        } else throw new IllegalArgumentException(String.format("Dish with orderDishId: %s not a IN_COOKED",orderDishesEntity.getId()));
    }

    public void setCookingTime(Integer cookId, Integer cookingTime) {
        CookEntity cookEntity = cookRepository.getById(cookId);
        cookEntity.setCookingTime(cookingTime);
        cookRepository.save(cookEntity);
    }


}
