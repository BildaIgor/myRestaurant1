package myRestaurant.repository;

import myRestaurant.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    Dish getById(int dishId);
    List<Dish> getAllByCategory(String category);
    List<Dish> getAllByNameContaining(String name);
    List<Dish> getAllByQuantityLessThan(int quantity);
    List<Dish> getAllByQuantity(int quantity);



}
