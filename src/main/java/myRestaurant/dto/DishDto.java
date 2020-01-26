package myRestaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import myRestaurant.utils.DishStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishDto {
    private int id;
    private int orderDishId;
    private String category;
    private String name;
    private double price;
    private DishStatus dishStatus;
}
