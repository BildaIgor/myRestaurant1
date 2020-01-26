package myRestaurant.repository;

import myRestaurant.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    OrderEntity getById(int id);
    List<OrderEntity> getAllByWaiterId(int waiterId);
    OrderEntity getByNumberAndOrderStatus(int number, String orderStatus);
    List<OrderEntity> getAllByWaiterIdAndOrderStatus(int waiterId, String orderStatus);
    List<OrderEntity> getAllByOrderStatus(String orderStatus);
    List<OrderEntity> getAllByTimeOfCreationBetweenAndWaiterId(Date from, Date to, int id);





}
