package myRestaurant.repository;

import myRestaurant.entity.OrderDishesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDishesRepository extends JpaRepository<OrderDishesEntity, Integer> {
    void deleteByDishId(int id);
    List<OrderDishesEntity> getAllByOrderId(int orderId);
    List<OrderDishesEntity> getByDishIdAndOrderId(int dishId, int orderId);
    void removeById(int id);
    List<OrderDishesEntity> getAllByDishStatus(String dishStatus);
    OrderDishesEntity getById(int id);
    List<OrderDishesEntity> getAllByDishStatusAndDishStatus(String dishStatus1, String dishStatus2);

}
