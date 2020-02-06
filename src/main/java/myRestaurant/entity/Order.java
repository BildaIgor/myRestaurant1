package myRestaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="number")
    private int number;

    @Column(name="creation_time")
    private Date timeOfCreation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "waiter_id",nullable = false)
    private Waiter waiter;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "check_amount")
    private double checkAmount;

    @Column(name = "discount")
    private double discount;

    @Column(name = "paid_time")
    private Date timeOfPaid;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDishes> orderDishes;

    @Override
    public String toString() {
        return "";
    }
}
