package myRestaurant.repository;

import myRestaurant.entity.Cook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookRepository extends JpaRepository<Cook, Integer> {
    Cook getById(int cookId);

}
