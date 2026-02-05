package org.example.storeback.controller.webmodel.request;

import java.util.List;

public record CreateOrderRequest(
    Long userId,
    String address,
    List<OrderItemRequest> items
) {
    public record OrderItemRequest(
        Long productId,
        Integer quantity
    ) {
    }
}
