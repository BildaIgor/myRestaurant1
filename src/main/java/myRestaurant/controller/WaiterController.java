package myRestaurant.controller;

import lombok.RequiredArgsConstructor;
import myRestaurant.dto.DishDTO;
import myRestaurant.dto.OrderDTO;
import myRestaurant.entity.OrderEntity;
import myRestaurant.service.WaiterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("waiter")
public class WaiterController {
    private final WaiterService waiterService;

    @PostMapping("/{waiterId}/createOrder")
    public void createOrder(@RequestParam(name="numberTable") Integer number,
                            @PathVariable(name="waiterId") Integer waiterId
    ){
        waiterService.createOrder(waiterId, number);
    }
    @PostMapping("/{waiterId}/addDishes")
    public void addDishesToOrder(@PathVariable(name = "waiterId") Integer waiterId,
                               @RequestParam(name = "orderId") Integer orderId,
                               @RequestParam(name = "dishesId") Integer [] dishesId
    ){
        waiterService.addDishesToOrder(waiterId,orderId,dishesId);
    }
    @GetMapping("/{waiterId}/getOrders/")
    public List<OrderDTO> getOrders(@PathVariable(name = "waiterId") Integer waiterId,
                             @RequestParam(name = "orderId", required = false) Integer orderId
    ){
        return waiterService.getOrders(waiterId,orderId);
    }


}
