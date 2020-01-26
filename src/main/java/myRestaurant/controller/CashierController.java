package myRestaurant.controller;

import lombok.RequiredArgsConstructor;
import myRestaurant.dto.ReportDto;
import myRestaurant.service.CashierService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("cashier")
public class CashierController {
    private final CashierService cashierService;

    @PostMapping("/paidOrder")
    public void paidOrder(@RequestParam(name = "orderId") Integer orderId){
        cashierService.payOrder(orderId);
    }

    @GetMapping("/getReport")
    public ReportDto getReport(){
        return cashierService.getReport();
    }
}
