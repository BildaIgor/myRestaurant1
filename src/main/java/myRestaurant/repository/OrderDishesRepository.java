package myRestaurant.repository;

import myRestaurant.entity.OrderDishes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDishesRepository extends JpaRepository<OrderDishes, Integer> {
    List<OrderDishes> getAllByOrderId(int orderId);
    void removeById(int id);
    OrderDishes getById(int id);
    List<OrderDishes> getAllByDishStatusAndDishStatus(String dishStatus1, String dishStatus2);

}
