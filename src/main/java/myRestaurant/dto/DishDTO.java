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
public class DishDTO {
    private int id;
    private String category;
    private String name;
    private int price;
    private DishStatus dishStatus;
}
