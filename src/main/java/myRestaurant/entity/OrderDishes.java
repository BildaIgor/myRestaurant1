package myRestaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders_dishes")
public class OrderDishes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "dish_status")
    private String dishStatus;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "start_cooking_time")
    private Date startCookingTime;

    @Column(name = "end_cooking_time")
    private Date andCookingTime;

    @Column(name = "close_time")
    private Date closeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    @Override
    public String toString() {
        return "";
    }


}

