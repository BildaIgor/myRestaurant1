package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.converter.DishConverter;
import myRestaurant.dto.DishDto;
import myRestaurant.entity.Cook;
import myRestaurant.entity.Dish;
import myRestaurant.entity.OrderDishes;
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

    public List<DishDto> getNewDishesByCategory(String category) {
        List<OrderDishes> orderDishesEntities = orderDishesRepository.getAllByDishStatusAndDish_Category(DishStatus.NEW.getTitle(), category);
        return orderDishesEntities.stream()
                .map(DishConverter :: toDto)
                .collect(Collectors.toList());
    }

    public void startCooking(Integer orderDishId) {
        OrderDishes orderDishes = orderDishesRepository.getById(orderDishId);
        if(orderDishes.getDishStatus().equals(DishStatus.NEW.getTitle())) {
            orderDishes.setDishStatus(DishStatus.IN_COOKING.getTitle());
            orderDishes.setStartCookingTime(new Date());
            orderDishesRepository.save(orderDishes);
        } else throw new IllegalArgumentException(String.format("Dish with orderDishId: %s not a new!", orderDishes.getId()));
    }

    public void endCooking(Integer orderDishId) {
        OrderDishes orderDishes = orderDishesRepository.getById(orderDishId);
        if(orderDishes.getDishStatus().equals(DishStatus.IN_COOKING.getTitle())) {
            orderDishes.setDishStatus(DishStatus.COOKED.getTitle());
            orderDishes.setAndCookingTime(new Date());
            orderDishesRepository.save(orderDishes);
        } else throw new IllegalArgumentException(String.format("Dish with orderDishId: %s not a IN_COOKED", orderDishes.getId()));
    }

    public void setCookingTime(Integer cookId, Integer cookingTime) {
        Cook cook = cookRepository.getById(cookId);
        cook.setCookingTime(cookingTime);
        cookRepository.save(cook);
    }


}
