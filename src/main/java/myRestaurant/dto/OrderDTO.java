package myRestaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import myRestaurant.utils.OrderStatus;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private int id;
    private int number;
    private Date timeOfCreation;
    private List<DishDTO> dishes;
    private int waiterId;
    private OrderStatus orderStatus;
}
