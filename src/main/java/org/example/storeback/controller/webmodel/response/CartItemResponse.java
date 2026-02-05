package org.example.storeback.controller.webmodel.response;

import java.math.BigDecimal;

/**
 * Response de un item del carrito
 */
public record CartItemResponse(
    Long id,
    Long productId,
    String productName,
    String productDescription,
    String productPicture,
    BigDecimal productPrice,
    BigDecimal productDiscount,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal,
    Integer productStock,
    Integer productRating
) {
}

