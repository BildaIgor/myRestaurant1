package myRestaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private WaiterDto waiterDto;
    private OrderStatus orderStatus;
    private double checkAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double discount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date timeOfPaid;
}
