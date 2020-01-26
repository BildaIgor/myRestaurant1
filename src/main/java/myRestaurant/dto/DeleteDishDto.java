package myRestaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import myRestaurant.utils.DishStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteDishDto {
    private int orderDishId;
    private DishStatus dishStatus;
    private String reason;
}
