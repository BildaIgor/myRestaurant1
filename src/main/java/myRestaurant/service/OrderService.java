package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.converter.MenuConverter;
import myRestaurant.converter.OrderConverter;
import myRestaurant.dto.*;
import myRestaurant.entity.*;
import myRestaurant.repository.*;
import myRestaurant.utils.DishStatus;
import myRestaurant.utils.OrderStatus;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
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
            Order order = Order.builder()
                    .number(createOrderDto.getTableNumber())
                    .timeOfCreation(new Date())
                    .waiter(waiterRepository.getById(createOrderDto.getWaiterId()))
                    .orderStatus(OrderStatus.OPENED.getTitle())
                    .build();
            orderRepository.save(order);
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
                        OrderDishes.builder()
                                .orderId(addDishesToOrderDto.getOrderId())
                                .dishId(dishId)
                                .dishStatus(DishStatus.NEW.getTitle())
                                .addTime(new Date())
                                .build());

            } else {
                throw new IllegalArgumentException(String.format("Dish with id: %s does not exist! Quantity : 0", dishId));
            }
            if (dishRepository.getById(dishId).getQuantity() < 10 && dishRepository.getById(dishId).getQuantity() > 0) {
                Dish dish = dishRepository.getById(dishId);
                dish.setQuantity(dish.getQuantity() - 1);
                dishRepository.save(dish);
            }
        });
        Double sum = addDishesToOrderDto.getDishesId().stream()
                .map(x -> dishRepository.getById(x).getPrice())
                .reduce(0.0, Double::sum);
        Order order = orderRepository.getById(addDishesToOrderDto.getOrderId());
        order.setCheckAmount(order.getCheckAmount() + sum);
        orderRepository.save(order);


    }

    public List<OrderDto> getOrdersByStatus(Integer waiterId, Integer orderId, OrderStatus orderStatus) {
        List<Order> orderEntities;
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
            List<Dish> dishEntities = dishRepository.getAllByNameContaining(name);
            return dishEntities.stream()
                    .map(MenuConverter::toMenuDto)
                    .collect(Collectors.toList());
        } else {
            List<Dish> dishEntities = dishRepository.getAllByCategory(name);
            return dishEntities.stream()
                    .map(MenuConverter::toMenuDto)
                    .collect(Collectors.toList());
        }
    }

    public void removeDishFromOrder(DeleteDishDto deleteDishDto) {
        OrderDishes orderDishes = orderDishesRepository.getById(deleteDishDto.getOrderDishId());
        orderDishesRepository.removeById(deleteDishDto.getOrderDishId());
        Order order = orderRepository.getById(orderDishes.getOrderId());
        order.setCheckAmount(order.getCheckAmount() - dishRepository.getById(orderDishes.getDishId()).getPrice());
        orderRepository.save(order);

        deletedDishesRepository.save(DeletedDishes.builder()
                .order_id(orderDishes.getOrderId())
                .dish_id(orderDishes.getDishId())
                .dishStatus(deleteDishDto.getDishStatus().getTitle())
                .reason(deleteDishDto.getReason())
                .removalTime(new Date())
                .build());

    }

    public void closeDish(Integer orderDishId) {
        OrderDishes orderDishes = orderDishesRepository.getById(orderDishId);
        if (orderDishes.getDishStatus().equals(DishStatus.COOKED.getTitle())) {
            orderDishes.setDishStatus(DishStatus.CLOSED.getTitle());
            orderDishes.setCloseTime(new Date());
            orderDishesRepository.save(orderDishes);
        } else {
            throw new IllegalArgumentException("This dish is not cooked!");
        }
    }

    public void closeOrder(Integer orderId) {
        Order order = orderRepository.getById(orderId);
        order.setOrderStatus(OrderStatus.CLOSED.getTitle());
        orderRepository.save(order);
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
            Order order = orderRepository.getById(orderId);
            order.setNumber(number);
            orderRepository.save(order);
        }
    }

    public void changeWaiter(Integer orderId, Integer waiterId) {
        Order order = orderRepository.getById(orderId);

        if (order != null) {
            order.setWaiter(waiterRepository.getById(waiterId));
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order does not exist!");
        }
    }
}
