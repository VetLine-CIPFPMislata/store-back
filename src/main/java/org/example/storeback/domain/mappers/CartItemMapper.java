package org.example.storeback.domain.mappers;

import org.example.storeback.domain.models.CartItem;
import org.example.storeback.domain.repository.entity.CartItemEntity;
import org.example.storeback.domain.service.dto.CartItemDto;

public class CartItemMapper {
    private static CartItemMapper instance;

    private CartItemMapper() {
    }

    public static CartItemMapper getInstance() {
        if (instance == null) {
            instance = new CartItemMapper();
        }
        return instance;
    }

    public CartItemDto fromCartItemToCartItemDto(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        return new CartItemDto(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getCartId(),
                cartItem.getProduct() != null ? ProductMapper.getInstance().fromProductToProductDto(cartItem.getProduct()) : null
        );
    }

    public CartItem fromCartItemEntityToCartItem(CartItemEntity cartItemEntity) {
        if (cartItemEntity == null) {
            return null;
        }
        return new CartItem(
                cartItemEntity.id(),
                cartItemEntity.quantity(),
                cartItemEntity.cartId(),
                cartItemEntity.product() != null ? ProductMapper.getInstance().fromProductEntityToProduct(cartItemEntity.product()) : null
        );
    }
}

