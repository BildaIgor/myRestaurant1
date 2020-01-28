package myRestaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="deleted_dishes")
public class DeletedDishes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "order_id")
    private int order_id;

    @Column(name = "dish_id")
    private int dish_id;

    @Column(name = "dish_status")
    private String dishStatus;

    @Column(name = "reason")
    private String reason;

    @Column(name = "removal_time")
    private Date removalTime;
}
