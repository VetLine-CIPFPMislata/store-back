package org.example.storeback.controller.webmodel.response;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long id,
    Long productId,
    String productName,
    String productDescription,
    String productPicture,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal
) {
}
