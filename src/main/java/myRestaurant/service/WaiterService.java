package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myRestaurant.converter.MenuConverter;
import myRestaurant.dto.MenuDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.dto.WaiterDto;
import myRestaurant.entity.OrderEntity;
import myRestaurant.entity.WaiterEntity;
import myRestaurant.repository.DishRepository;
import myRestaurant.repository.OrderDishesRepository;
import myRestaurant.repository.OrderRepository;
import myRestaurant.repository.WaiterRepository;
import myRestaurant.utils.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaiterService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final OrderDishesRepository orderDishesRepository;
    private final OrderService orderService;
    private final WaiterRepository waiterRepository;

    public Double getPercentageOfSales(Integer waiterId, Integer orderId){
        if(orderId==null) {
            List<OrderEntity> orderEntities = orderRepository.getAllByWaiterIdAndOrderStatus(waiterId, OrderStatus.CLOSED.getTitle());
            return orderEntities.stream()
                    .map(x -> orderService.getPercentageOfSalesByOrder(x.getId()))
                    .reduce(0.0, Double::sum);
        } else {
            return orderService.getPercentageOfSalesByOrder(orderId);
        }
    }

    public List<OrderDto> getClosedOrders(Integer waiterId){
        return orderService.getOrders(waiterId , null).stream()
                .filter(x->x.getOrderStatus().equals(OrderStatus.CLOSED))
                .collect(Collectors.toList());
    }
    public Map<MenuDto, Integer> getSalesDishesStatistic(Integer waiterId){
        List<OrderEntity> orderEntities;
        if(waiterId!=null) {
           orderEntities = orderRepository.getAllByWaiterIdAndOrderStatus(waiterId, OrderStatus.CLOSED.getTitle());
        } else {
            orderEntities = orderRepository.getAllByOrderStatus(OrderStatus.CLOSED.getTitle());
        }
        Map<MenuDto, Integer> dishesAndQuantity = new HashMap<>();
        orderEntities.forEach(
                x->{
                    x.getDishes().forEach(
                            a->{
                                if(!dishesAndQuantity.containsKey(MenuConverter.toMenuDto(a))) {
                                    dishesAndQuantity.put(MenuConverter.toMenuDto(a), 1);
                                } else {
                                    Integer quantity = dishesAndQuantity.get(MenuConverter.toMenuDto(a));
                                    dishesAndQuantity.put(MenuConverter.toMenuDto(a),quantity+1);
                                }
                            }
                    );
        }
        );

        return dishesAndQuantity;
    }
    public List<WaiterDto> getWaiters(Integer waiterId){
        List<WaiterDto> waiterDtos = new ArrayList<>();
        if(waiterId!=null) {
            WaiterEntity waiterEntity = waiterRepository.getById(waiterId);
            waiterDtos.add(WaiterDto.builder()
                    .name(waiterEntity.getName())
                    .closedOrders(getClosedOrders(waiterId))
                    .percentageOfSales(getPercentageOfSales(waiterId,null))
                    .build());
        } else {
            List<WaiterEntity> waiterEntities = waiterRepository.findAll();
            waiterEntities.forEach(
                    x->{
                        waiterDtos.add(WaiterDto.builder()
                        .name(x.getName())
                        .closedOrders(getClosedOrders(x.getId()))
                        .percentageOfSales(getPercentageOfSales(x.getId(), null))
                        .build());
                    }
            );
        }
        return waiterDtos;
    }


}
