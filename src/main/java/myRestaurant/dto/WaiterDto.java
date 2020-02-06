package myRestaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class WaiterDto {
    private int id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OrderDto> orders;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double percentageOfSales;

}
