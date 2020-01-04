package myRestaurant.repository;

import myRestaurant.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
    MenuEntity getById(int id);

}
