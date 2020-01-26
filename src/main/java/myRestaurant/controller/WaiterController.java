package myRestaurant.controller;

import lombok.RequiredArgsConstructor;
import myRestaurant.dto.MenuDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.dto.WaiterDto;
import myRestaurant.service.WaiterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("waiter")
public class WaiterController {
    private final WaiterService waiterService;
    @GetMapping("/getTotalPercentageOfSales")
    public Double getPercentageOfSales(@RequestParam(name = "waiterId") Integer waiterId,
                                       @RequestParam(name = "orderId",required = false) Integer orderId){
        return waiterService.getPercentageOfSalesByOrder(waiterId,orderId);
    }

    @GetMapping("/getTotalPercentageOfSalesByTime")
    public Double getPercentageOfSales(@RequestParam(name = "waiterId") Integer waiterId,
                                       @RequestParam(name = "dateFrom") String dateForm,
                                       @RequestParam(name = "dateTo") String dateTo) {

        return waiterService.getPercentageOfSalesByTime(waiterId, dateForm , dateTo);

    }

    @GetMapping("/getClosedOrders")
    public List<OrderDto> getClosedOrders(@RequestParam(name = "waiterId") Integer waiterId){
         return waiterService.getPaidOrders(waiterId);
    }

    @GetMapping("/getSalesDishesStatistic")
    public Map<MenuDto, Long> getSalesDishesStatistic(@RequestParam(name = "waiterId",required = false) Integer waiterId){
        return waiterService.getSalesDishesStatistic(waiterId);
    }
//    @GetMapping("/getWaiters")
//    public List<WaiterDto> getWaiters(@RequestParam(name = "waiterId",required = false)Integer waiterId){
//        return waiterService.getWaiter(waiterId);
//    }
    @GetMapping("/getStopList")
    public List<MenuDto> getStopList(){
        return waiterService.getStopList();
    }
    @GetMapping("/getPlayList")
    public List<MenuDto> getPlayList(){
        return waiterService.getPlayList();
    }
    @GetMapping("/getNormalList")
    public List<MenuDto> getNormalList(){
        return waiterService.getNormalList();
    }







}
