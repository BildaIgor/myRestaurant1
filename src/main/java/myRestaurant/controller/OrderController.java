package myRestaurant.controller;

import lombok.RequiredArgsConstructor;
import myRestaurant.dto.AddDishesToOrderDto;
import myRestaurant.dto.CreateOrderDto;
import myRestaurant.dto.MenuDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.service.OrderService;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/getDishesInMenuByCategory")
    public List<MenuDto> getDishesInMenuByCategory(@RequestParam(name = "category") String category){
        return orderService.getDishesInMenuByCategory(category);
    }
    @GetMapping("/findDishesInMenuByName")
    public List<MenuDto> findDishesInMenuByName(@RequestParam(name = "name") String name){
        return orderService.getDishesInMenuByName(name);
    }


}
