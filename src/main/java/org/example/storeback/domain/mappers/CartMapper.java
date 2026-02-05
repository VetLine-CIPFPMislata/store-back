package org.example.storeback.domain.mappers;

import org.example.storeback.domain.models.Cart;
import org.example.storeback.domain.repository.entity.CartEntity;
import org.example.storeback.domain.service.dto.CartDto;

import java.util.Collections;
import java.util.stream.Collectors;

public class CartMapper {
    private static CartMapper instance;

    private CartMapper() {
    }

    public static CartMapper getInstance() {
        if (instance == null) {
            instance = new CartMapper();
        }
        return instance;
    }

    public CartDto fromCartToCartDto(Cart cart) {
        if (cart == null) {
            return null;
        }
        return new CartDto(
                cart.getId(),
                cart.getTotalProducts(),
                cart.getTotalPrice(),
                cart.getUser() != null ? ClientMapper.getInstance().fromClientToClientDto(cart.getUser()) : null,
                cart.getItems() != null
                    ? cart.getItems().stream()
                        .map(CartItemMapper.getInstance()::fromCartItemToCartItemDto)
                        .collect(Collectors.toList())
                    : Collections.emptyList()
        );
    }

    public Cart fromCartEntityToCart(CartEntity cartEntity) {
        if (cartEntity == null) {
            return null;
        }
        return new Cart(
                cartEntity.id(),
                cartEntity.totalProducts(),
                cartEntity.totalPrice(),
                cartEntity.user() != null ? ClientMapper.getInstance().fromClientEntityToClient(cartEntity.user()) : null,
                cartEntity.items() != null
                    ? cartEntity.items().stream()
                        .map(CartItemMapper.getInstance()::fromCartItemEntityToCartItem)
                        .collect(Collectors.toList())
                    : Collections.emptyList()
        );
    }
}

