package myRestaurant.repository;

import myRestaurant.entity.CookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookRepository extends JpaRepository<CookEntity, Integer> {
    CookEntity getById(int cookId);
}
