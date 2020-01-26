package myRestaurant.controller;

import lombok.RequiredArgsConstructor;
import myRestaurant.dto.*;
import myRestaurant.service.OrderService;
import myRestaurant.utils.OrderStatus;
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
    @GetMapping("/getOrders")
    public List<OrderDto> getOrders(@RequestParam(name = "waiterId",required = false) Integer waiterId,
                                    @RequestParam(name = "orderId", required = false) Integer orderId,
                                    @RequestParam(name = "orderStatus") String orderStatus
                                    ){
        return orderService.getOrdersByStatus(waiterId,orderId,OrderStatus.valueOf(orderStatus));
    }
    @GetMapping("/getDishesInMenu")
    public List<MenuDto> getDishesInMenuByCategory(@RequestParam(name = "column") String column,
                                                   @RequestParam(name = "name") String name){
        return orderService.getDishesInMenu(column,name);
    }

    @PostMapping("/removeDish")
    public void removeDish(@RequestBody DeleteDishDto deleteDishDto){
        orderService.removeDishFromOrder(deleteDishDto);

    }
    @PostMapping("/closeOrder")
    public void closeOrder(@RequestParam(name = "orderId")Integer orderId){
        orderService.closeOrder(orderId);
    }

    @PostMapping("/closeDish")
    public void closeDish(@RequestParam(name = "orderDishId")Integer orderDishId){
        orderService.closeDish(orderDishId);
    }



    @PostMapping("/changeNumber")
    public void changeNumber(@RequestParam(name = "orderId") Integer orderId,
                             @RequestParam(name = "number") Integer number){
        orderService.changeNumber(orderId,number);
    }
    @PostMapping("/changeWaiter")
    public void changeWaiter(@RequestParam("orderId") Integer orderId,
                             @RequestParam("waiterId") Integer waiterId){
        orderService.changeWaiter(orderId,waiterId);
    }


}
