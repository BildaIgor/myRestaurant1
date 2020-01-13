package myRestaurant.repository;

import myRestaurant.entity.OrderDishesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDishesRepository extends JpaRepository<OrderDishesEntity, Integer> {
    void deleteByDishId(int id);
    List<OrderDishesEntity> getAllByOrderId(int orderId);
}
