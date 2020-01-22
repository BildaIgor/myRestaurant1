package myRestaurant.repository;

import myRestaurant.entity.DeletedDishesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedDishesRepository extends JpaRepository<DeletedDishesEntity, Integer> {
}
