package myRestaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="dish")
public class DishEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name="category")
    private String category;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private double price;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "orders_dishes",
    joinColumns = @JoinColumn(name = "dish_id"),
    inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<OrderEntity> orderEntityList;

    @Column(name = "percentage_of_sales")
    private int percentageOfSales;

    @Column(name = "quantity")
    private int quantity;



}
