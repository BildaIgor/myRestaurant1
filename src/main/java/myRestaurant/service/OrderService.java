package myRestaurant.service;

import lombok.RequiredArgsConstructor;
import myRestaurant.converter.MenuConverter;
import myRestaurant.converter.OrderConverter;
import myRestaurant.dto.*;
import myRestaurant.entity.DeletedDishes;
import myRestaurant.entity.Dish;
import myRestaurant.entity.Order;
import myRestaurant.entity.OrderDishes;
import myRestaurant.repository.*;
import myRestaurant.utils.DishStatus;
import myRestaurant.utils.OrderStatus;
import myRestaurant.utils.RemainderDishStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static myRestaurant.utils.RemainderDishStatus.*;

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
            throw new IllegalArgumentException(String.format("Order with table number %s is already exist!", createOrderDto.getTableNumber()));
        } else {
            Order order = Order.builder()
                    .number(createOrderDto.getTableNumber())
                    .timeOfCreation(new Date())
                    .waiter(waiterRepository.getById(createOrderDto.getWaiterId()))
                    .orderStatus(OrderStatus.OPENED.getTitle())
                    .discount(1.0)
                    .build();
            orderRepository.save(order);
        }
    }

    public void addDishesToOrder(AddDishesToOrderDto addDishesToOrderDto) {
        Order order = orderRepository.getById(addDishesToOrderDto.getOrderId());
        if (!order.getOrderStatus().equals(OrderStatus.OPENED.getTitle())) {
            throw new IllegalArgumentException("This order is closed!");
        }
        List<OrderDishes> orderDishes = order.getOrderDishes();
        orderDishes.addAll(addDishesToOrderDto.getDishesId().stream()
                .peek(this::changeDishQuantity)
                .map(dishId -> OrderDishes.builder()
                        .order(order)
                        .addTime(new Date())
                        .dishStatus(DishStatus.NEW.getTitle())
                        .dish(dishRepository.getById(dishId))
                        .build())
                .collect(Collectors.toList()));
        order.setOrderDishes(orderDishes);
        order.setCheckAmount(countCheckAmount(order.getOrderDishes()));
        orderRepository.save(order);
    }

    private double countCheckAmount(List<OrderDishes> orderDishes) {
        return orderDishes.stream()
                .map(OrderDishes::getDish)
                .map(Dish::getPrice)
                .reduce(0.0, Double::sum);
    }

    private void changeDishQuantity(int dishId) {
        Dish dish = dishRepository.getById(dishId);
        if (checkDishQuantity(dish).equals(STOP_LIST)) {
            throw new IllegalArgumentException(String.format("Dish with id: %s on a stop-list!", dish.getId()));
        } else if (dish.getQuantity() != PLAY_LIST.getQuantity() &&
                dish.getQuantity() != NORMAL.getQuantity()) {
            dish.setQuantity(dish.getQuantity() - 1);
            dishRepository.save(dish);
        }
    }

    private RemainderDishStatus checkDishQuantity(Dish dish) {
        int dishQuantity = dish.getQuantity();
        if (dishQuantity == STOP_LIST.getQuantity()) {
            return STOP_LIST;
        } else if (dishQuantity == NORMAL.getQuantity()) {
            return NORMAL;
        } else if (dishQuantity == PLAY_LIST.getQuantity()) {
            return PLAY_LIST;
        } else return RESTRICTION;
    }


    public List<OrderDto> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.getAllByOrderStatus(orderStatus.getTitle()).stream()
                .map(OrderConverter::toOrderDTO)
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

    @Transactional
    public void removeDishFromOrder(DeleteDishDto deleteDishDto) {
        OrderDishes orderDish = orderDishesRepository.getById(deleteDishDto.getOrderDishId());
        Order order = orderRepository.getById(orderDish.getOrder().getId());
        order.getOrderDishes().remove(orderDish);
        order.setCheckAmount(countCheckAmount(order.getOrderDishes()));
        orderRepository.save(order);
        transferDishToDeletedDishes(deleteDishDto, orderDish);

    }

    private void transferDishToDeletedDishes(DeleteDishDto deleteDishDto, OrderDishes orderDish) {
        deletedDishesRepository.save(DeletedDishes.builder()
                .order_id(orderDish.getOrder().getId())
                .dish_id(orderDish.getDish().getId())
                .dishStatus(deleteDishDto.getDishStatus().getTitle())
                .reason(deleteDishDto.getReason())
                .removalTime(new Date())
                .build());
    }

    public void closeDish(Integer orderDishId) {
        OrderDishes orderDish = orderDishesRepository.getById(orderDishId);
        if (orderDish.getDishStatus().equals(DishStatus.COOKED.getTitle())) {
            orderDish.setDishStatus(DishStatus.CLOSED.getTitle());
            orderDish.setCloseTime(new Date());
            orderDishesRepository.save(orderDish);
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
        return orderRepository.getById(orderId).getOrderDishes().stream()
                .map(OrderDishes::getDish)
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

