package org.example.storeback.controller.webmodel.request;

public record AddProductToCartRequest(
    Long productId,
    Integer quantity
) {
}

