package myRestaurant.controller;

import lombok.RequiredArgsConstructor;
import myRestaurant.dto.CookDto;
import myRestaurant.dto.WaiterDto;
import myRestaurant.service.AdministratorService;
import org.springframework.web.bind.annotation.*;

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
}
