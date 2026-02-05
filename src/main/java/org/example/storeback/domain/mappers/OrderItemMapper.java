package org.example.storeback.domain.mappers;

import org.example.storeback.domain.models.OrderItem;
import org.example.storeback.domain.repository.entity.OrderItemEntity;
import org.example.storeback.domain.service.dto.OrderItemDto;

public class OrderItemMapper {
    private static OrderItemMapper instance;

    private OrderItemMapper() {
    }

    public static OrderItemMapper getInstance() {
        if (instance == null) {
            instance = new OrderItemMapper();
        }
        return instance;
    }

    public OrderItemDto fromOrderItemToOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getOrderId(),
                orderItem.getProduct() != null ? ProductMapper.getInstance().fromProductToProductDto(orderItem.getProduct()) : null
        );
    }

    public OrderItem fromOrderItemEntityToOrderItem(OrderItemEntity orderItemEntity) {
        if (orderItemEntity == null) {
            return null;
        }
        return new OrderItem(
                orderItemEntity.id(),
                orderItemEntity.quantity(),
                orderItemEntity.orderId(),
                orderItemEntity.product() != null ? ProductMapper.getInstance().fromProductEntityToProduct(orderItemEntity.product()) : null
        );
    }
}
