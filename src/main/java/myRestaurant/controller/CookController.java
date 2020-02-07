package myRestaurant.controller;

import lombok.RequiredArgsConstructor;
import myRestaurant.dto.DishDto;
import myRestaurant.service.CookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("cook")
public class CookController {
    private final CookService cookService;

    @GetMapping("/getNewDishesByCategory")
    public List<DishDto> getNewDishes(@RequestParam(name = "category") String category) {
        return cookService.getNewDishesByCategory(category);
    }

    @PostMapping("/startCooking")
    public void startCooking(@RequestParam(name = "dishId") Integer dishId) {
        cookService.startCooking(dishId);
    }

    @PostMapping("/endCooking")
    public void endCooking(@RequestParam(name = "dishId") Integer dishId) {
        cookService.endCooking(dishId);
    }

    @PostMapping("/setCookingTime")
    public void setCookingTime(@RequestParam(name = "cookId") Integer cookId,
                               @RequestParam(name = "cookingTime") Integer cookingTime) {
        cookService.setCookingTime(cookId, cookingTime);
    }
}
