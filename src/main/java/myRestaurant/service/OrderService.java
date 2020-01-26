package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myRestaurant.converter.MenuConverter;
import myRestaurant.converter.OrderConverter;
import myRestaurant.dto.*;
import myRestaurant.entity.*;

import myRestaurant.repository.*;
import myRestaurant.utils.DishStatus;
import myRestaurant.utils.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final OrderDishesRepository orderDishesRepository;
    private final WaiterRepository waiterRepository;
    private final DeletedDishesRepository deletedDishesRepository;


    public void createOrder(CreateOrderDto createOrderDto) {
        if (orderRepository.getByNumberAndOrderStatus(createOrderDto.getTableNumber(), OrderStatus.OPENED.getTitle()) != null) {
            throw new IllegalArgumentException(String.format("Table with number %s is exist!", createOrderDto.getTableNumber()));
        } else {
            OrderEntity orderEntity = OrderEntity.builder()
                    .number(createOrderDto.getTableNumber())
                    .timeOfCreation(new Date())
                    .waiter(waiterRepository.getById(createOrderDto.getWaiterId()))
                    .orderStatus(OrderStatus.OPENED.getTitle())
                    .build();
            orderRepository.save(orderEntity);
        }
    }

    public void addDishesToOrder(AddDishesToOrderDto addDishesToOrderDto) {

        if(orderRepository.getById(addDishesToOrderDto.getOrderId()).getOrderStatus().equals(OrderStatus.CLOSED.getTitle())){
            throw new IllegalArgumentException("This order is closed!");
        }
        addDishesToOrderDto.getDishesId().forEach(dishId -> {
            if (dishRepository.getById(dishId).getQuantity() < 10 && dishRepository.getById(dishId).getQuantity() > 0 ||
                    dishRepository.getById(dishId).getQuantity() == 500 ||
                    dishRepository.getById(dishId).getQuantity() == 100) {
                orderDishesRepository.save(
                        OrderDishesEntity.builder()
                                .orderId(addDishesToOrderDto.getOrderId())
                                .dishId(dishId)
                                .dishStatus(DishStatus.NEW.getTitle())
                                .addTime(new Date())
                                .build());

            } else {
                throw new IllegalArgumentException(String.format("Dish with id: %s does not exist! Quantity : 0", dishId));
            }
            if (dishRepository.getById(dishId).getQuantity() < 10 && dishRepository.getById(dishId).getQuantity() > 0) {
                DishEntity dishEntity = dishRepository.getById(dishId);
                dishEntity.setQuantity(dishEntity.getQuantity() - 1);
                dishRepository.save(dishEntity);
            }
        });
        Double sum = addDishesToOrderDto.getDishesId().stream()
                .map(x -> dishRepository.getById(x).getPrice())
                .reduce(0.0, Double::sum);
        OrderEntity orderEntity = orderRepository.getById(addDishesToOrderDto.getOrderId());
        orderEntity.setCheckAmount(orderEntity.getCheckAmount() + sum);
        orderRepository.save(orderEntity);


    }

    public List<OrderDto> getOrdersByStatus(Integer waiterId, Integer orderId, OrderStatus orderStatus) {
        List<OrderEntity> orderEntities;
        if (waiterId == null) {
            orderEntities = orderRepository.getAllByOrderStatus(orderStatus.getTitle());
        } else {
            orderEntities = orderRepository.getAllByWaiterIdAndOrderStatus(waiterId, orderStatus.getTitle());
        }
        if (orderId != null) {
            orderEntities = orderEntities.stream()
                    .filter(x -> x.getId() == orderId)
                    .collect(Collectors.toList());
        }
        return orderEntities.stream()
                .map(x -> OrderConverter.toOrderDTO(x, orderDishesRepository.getAllByOrderId(x.getId())))
                .collect(Collectors.toList());
    }

    public List<MenuDto> getDishesInMenu(String column, String name) {
        if (column.equals("name")) {
            List<DishEntity> dishEntities = dishRepository.getAllByNameContaining(name);
            return dishEntities.stream()
                    .map(MenuConverter::toMenuDto)
                    .collect(Collectors.toList());
        } else {
            List<DishEntity> dishEntities = dishRepository.getAllByCategory(name);
            return dishEntities.stream()
                    .map(MenuConverter::toMenuDto)
                    .collect(Collectors.toList());
        }
    }

    public void removeDishFromOrder(DeleteDishDto deleteDishDto) {
        OrderDishesEntity orderDishesEntity = orderDishesRepository.getById(deleteDishDto.getOrderDishId());
        orderDishesRepository.removeById(deleteDishDto.getOrderDishId());
        OrderEntity orderEntity = orderRepository.getById(orderDishesEntity.getOrderId());
        orderEntity.setCheckAmount(orderEntity.getCheckAmount() - dishRepository.getById(orderDishesEntity.getDishId()).getPrice());
        orderRepository.save(orderEntity);

        deletedDishesRepository.save(DeletedDishesEntity.builder()
                .order_id(orderDishesEntity.getOrderId())
                .dish_id(orderDishesEntity.getDishId())
                .dishStatus(deleteDishDto.getDishStatus().getTitle())
                .reason(deleteDishDto.getReason())
                .removalTime(new Date())
                .build());

    }

    public void closeDish(Integer orderDishId) {
        OrderDishesEntity orderDishesEntity = orderDishesRepository.getById(orderDishId);
        if (orderDishesEntity.getDishStatus().equals(DishStatus.COOKED.getTitle())) {
            orderDishesEntity.setDishStatus(DishStatus.CLOSED.getTitle());
            orderDishesEntity.setCloseTime(new Date());
            orderDishesRepository.save(orderDishesEntity);
        } else {
            throw new IllegalArgumentException("This dish is not cooked!");
        }
    }

    public void closeOrder(Integer orderId) {
        OrderEntity orderEntity = orderRepository.getById(orderId);
        orderEntity.setOrderStatus(OrderStatus.CLOSED.getTitle());
        orderRepository.save(orderEntity);
    }


    public Double getPercentageOfSalesByOrder(Integer orderId) {
        return orderRepository.getById(orderId).getDishes().stream()
                .map(x -> (double) x.getPercentageOfSales() * x.getPrice() / 100)
                .reduce(0.0, Double::sum);
    }

    public void changeNumber(Integer orderId, Integer number) {
        if (orderRepository.getByNumberAndOrderStatus(number, OrderStatus.OPENED.getTitle()) != null) {
            throw new IllegalArgumentException(String.format("Order with number: %s is exist!", number));
        } else {
            OrderEntity orderEntity = orderRepository.getById(orderId);
            orderEntity.setNumber(number);
            orderRepository.save(orderEntity);
        }
    }

    public void changeWaiter(Integer orderId, Integer waiterId) {
        OrderEntity orderEntity = orderRepository.getById(orderId);

        if (orderEntity != null) {
            orderEntity.setWaiter(waiterRepository.getById(waiterId));
            orderRepository.save(orderEntity);
        } else {
            throw new IllegalArgumentException("Order does not exist!");
        }
    }
}
