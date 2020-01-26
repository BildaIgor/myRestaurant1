package myRestaurant.controller;

import lombok.RequiredArgsConstructor;
import myRestaurant.dto.CookDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.dto.WaiterDto;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.OrderEntity;
import myRestaurant.service.AdministratorService;
import myRestaurant.utils.OrderStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("administrator")
public class AdministratorController {
    private final AdministratorService administratorService;

    @PostMapping("/createWaiter")
    public void createWaiter(@RequestBody WaiterDto waiterDto){
        administratorService.createWaiter(waiterDto);
    }

    @PostMapping("/createCook")
    public void createCook(@RequestBody CookDto cookDto){
        administratorService.createCook(cookDto);
    }
    @PostMapping("/deleteWaiter")
    public void deleteWaiter(@RequestParam(name = "waiterId") Integer waiterId){
        administratorService.deleteWaiter(waiterId);
    }
    @PostMapping("/deleteCook")
    public void deleteCook(@RequestParam(name = "cookId") Integer cookId){
        administratorService.deleteCook(cookId);
    }

    @PostMapping("/addDishInPlayList")
    public void addDishInPlayList(@RequestParam(name = "dishId") Integer dishId){
        administratorService.addDishInPlayList(dishId);
    }

    @PostMapping("/addDishInStopList")
    public void addDishInStopList(@RequestParam(name = "dishId") Integer dishId,
                                  @RequestParam(name = "balance") Integer balance){
        administratorService.addDishInStopList(dishId, balance );
    }

    @PostMapping("/addDishInNormalList")
    public void addDishInNormalList(@RequestParam(name = "dishId") Integer dishId){
        administratorService.setNormalQuantity(dishId);
    }
    @PostMapping("/makeDiscount")
    public void makeDiscount(@RequestParam(name = "orderId") Integer orderId,
                             @RequestParam(name = "discount") Double discount){
            administratorService.makeDiscount(orderId, discount);
    }
    @PostMapping("/deleteDiscount")
    public void makeDiscount(@RequestParam(name = "orderId") Integer orderId){
        administratorService.deleteDiscount(orderId);
    }
    @PostMapping("/closeDay")
    public void closeDay(){
        administratorService.closeDay();
    }
    @GetMapping("/cashBox")
    public Map<String, Double> getCashBox(){
        return administratorService.getCashbox();
    }





}
