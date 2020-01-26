package myRestaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDto {
    private int id;
    private String category;
    private String name;
    private double price;
    private int percentageOfSales;

}
