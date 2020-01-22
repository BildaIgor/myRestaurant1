package myRestaurant.controller;

import lombok.RequiredArgsConstructor;
import myRestaurant.dto.*;
import myRestaurant.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/createOrder")
    public void createOrder(@RequestBody CreateOrderDto createOrderDto
                            ){
        orderService.createOrder(createOrderDto);
    }
    @PostMapping("/addDishes")
    public void addDishesToOrder(@RequestBody AddDishesToOrderDto addDishesToOrderDto
                                 ){
        orderService.addDishesToOrder(addDishesToOrderDto);
    }
    @GetMapping("/{waiterId}/getOrders")
    public List<OrderDto> getOrders(@PathVariable(name = "waiterId") Integer waiterId,
                                    @RequestParam(name = "orderId", required = false) Integer orderId
    ){
        return orderService.getOrders(waiterId,orderId);
    }
    @GetMapping("/getDishesInMenu")
    public List<MenuDto> getDishesInMenuByCategory(@RequestParam(name = "column") String column,
                                                   @RequestParam(name = "name") String name){
        return orderService.getDishesInMenu(column,name);
    }

    @PostMapping("/removeDish")
    public void removeDish(@RequestBody DeleteDishDto deleteDishDto){
        orderService.removeDishFromOrder(
                deleteDishDto.getOrder_id(),
                deleteDishDto.getDish_id(),
                deleteDishDto.getDishStatus(),
                deleteDishDto.getReason());
    }
    @PostMapping("/closeOrder")
    public void closeOrder(@RequestParam(name = "orderId")Integer orderId){
        orderService.closeOrder(orderId);
    }


}
