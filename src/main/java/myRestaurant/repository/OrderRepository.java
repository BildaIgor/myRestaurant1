package myRestaurant.repository;

import myRestaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order getById(int id);
    Order getByNumberAndOrderStatus(int number, String orderStatus);
    List<Order> getAllByWaiterIdAndOrderStatus(int waiterId, String orderStatus);
    List<Order> getAllByOrderStatus(String orderStatus);
    List<Order> getAllByTimeOfCreationBetweenAndWaiterId(Date from, Date to, int id);





}
