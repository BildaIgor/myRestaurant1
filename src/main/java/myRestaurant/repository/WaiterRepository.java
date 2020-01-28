package myRestaurant.repository;

import myRestaurant.entity.Waiter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Configuration
@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Integer> {
    Waiter getById(int id);
    List<Waiter> findAll();
}
