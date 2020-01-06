package myRestaurant.repository;

import myRestaurant.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<DishEntity, Integer> {
    DishEntity getById(int dishId);
    List<DishEntity> getAllByDishStatus(String category);
}
