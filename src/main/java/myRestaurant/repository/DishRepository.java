package myRestaurant.repository;

import myRestaurant.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<DishEntity, Integer> {
}
