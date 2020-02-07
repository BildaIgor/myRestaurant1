package myRestaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int quantity;


}
