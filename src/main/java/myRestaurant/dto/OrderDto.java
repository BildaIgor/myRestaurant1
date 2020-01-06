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
public class OrderDto {
    private int id;
    private int number;
    private Date timeOfCreation;
    private List<DishDto> dishes;
    private int waiterId;
    private OrderStatus orderStatus;
    private int checkAmount;
}
