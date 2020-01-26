package myRestaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class ReportDto {
    private int id;
    private Date from;
    private Date to;
    private String cashierName;
    private List<OrderDto> paidOrders;
    private Map<MenuDto , Long> paidDishes;
    private Double sumCash;
    private Double sumPercentageOfSales;


}
