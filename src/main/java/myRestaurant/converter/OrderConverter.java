package myRestaurant.converter;

import myRestaurant.dto.DishDto;
import myRestaurant.dto.OrderDto;
import myRestaurant.entity.DishEntity;
import myRestaurant.entity.OrderDishesEntity;
import myRestaurant.entity.OrderEntity;
import myRestaurant.repository.DishRepository;
import myRestaurant.utils.DishStatus;
import myRestaurant.utils.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderDto toOrderDTO(OrderEntity orderEntity, List<OrderDishesEntity> orderDishesEntities){
        List<DishDto> dishDtos = new ArrayList<>();
        for (int i = 0; i <orderEntity.getDishes().size() ; i++) {
            dishDtos.add(DishDto.builder()
                    .id(orderEntity.getDishes().get(i).getId())
                    .orderDishId(orderDishesEntities.get(i).getId())
                    .category(orderEntity.getDishes().get(i).getCategory())
                    .name(orderEntity.getDishes().get(i).getName())
                    .price(orderEntity.getDishes().get(i).getPrice())
                    .dishStatus(DishStatus.valueOf(orderDishesEntities.get(i).getDishStatus()))
                    .build()
            );
        }
        return OrderDto.builder()
                .id(orderEntity.getId())
                .number(orderEntity.getNumber())
                .timeOfCreation(orderEntity.getTimeOfCreation())
                .dishes(dishDtos)
                .waiterDto(WaiterConverter.toWaiterDto(orderEntity.getWaiter()))
                .orderStatus(OrderStatus.valueOf(orderEntity.getOrderStatus()))
                .checkAmount(orderEntity.getCheckAmount())
                .discount(orderEntity.getDiscount())
                .timeOfPaid(orderEntity.getTimeOfPaid())
                .build();
    }
}
