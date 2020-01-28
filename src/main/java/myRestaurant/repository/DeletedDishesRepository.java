package myRestaurant.repository;

import myRestaurant.entity.DeletedDishes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedDishesRepository extends JpaRepository<DeletedDishes, Integer> {
}
