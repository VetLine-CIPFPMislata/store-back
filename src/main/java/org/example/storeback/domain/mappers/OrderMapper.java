package org.example.storeback.domain.mappers;

import org.example.storeback.domain.models.Order;
import org.example.storeback.domain.repository.entity.OrderEntity;
import org.example.storeback.domain.service.dto.OrderDto;

import java.util.Collections;
import java.util.stream.Collectors;

public class OrderMapper {
    private static OrderMapper instance;

    private OrderMapper() {
    }

    public static OrderMapper getInstance() {
        if (instance == null) {
            instance = new OrderMapper();
        }
        return instance;
    }

    public OrderDto fromOrderToOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        return new OrderDto(
                order.getId(),
                order.getTotalProducts(),
                order.getTotalPrice(),
                order.getState(),
                order.getUser() != null ? ClientMapper.getInstance().fromClientToClientDto(order.getUser()) : null,
                order.getCreatedAt(),
                order.getOrderAt(),
                order.getAddress(),
                order.getItems() != null
                    ? order.getItems().stream()
                        .map(OrderItemMapper.getInstance()::fromOrderItemToOrderItemDto)
                        .collect(Collectors.toList())
                    : Collections.emptyList()
        );
    }

    public Order fromOrderEntityToOrder(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        return new Order(
                orderEntity.id(),
                orderEntity.totalProducts(),
                orderEntity.totalPrice(),
                orderEntity.state(),
                orderEntity.user() != null ? ClientMapper.getInstance().fromClientEntityToClient(orderEntity.user()) : null,
                orderEntity.createdAt(),
                orderEntity.orderAt(),
                orderEntity.address(),
                orderEntity.items() != null
                    ? orderEntity.items().stream()
                        .map(OrderItemMapper.getInstance()::fromOrderItemEntityToOrderItem)
                        .collect(Collectors.toList())
                    : Collections.emptyList()
        );
    }
}

