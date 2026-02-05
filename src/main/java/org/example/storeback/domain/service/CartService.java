package org.example.storeback.domain.service;

import org.example.storeback.controller.webmodel.request.AddProductToCartRequest;
import org.example.storeback.domain.service.dto.CartDto;

import java.util.List;

public interface CartService {
    List<CartDto> findAll();
    CartDto findById(Long id);
    CartDto findByUserId(Long userId);
    CartDto create(CartDto cartDto);
    CartDto update(Long id, CartDto cartDto);
    void delete(Long id);
    CartDto addProductToCart(Long userId, AddProductToCartRequest request);
    CartDto updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity);
    void removeCartItem(Long userId, Long cartItemId);
}