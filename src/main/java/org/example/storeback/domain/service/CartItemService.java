package org.example.storeback.domain.service;

import org.example.storeback.domain.service.dto.CartItemDto;

import java.util.List;

public interface CartItemService {
    List<CartItemDto> findAll();
    CartItemDto findById(Long id);
    List<CartItemDto> findByCartId(Long cartId);
    CartItemDto create(CartItemDto cartItemDto);
    CartItemDto update(Long id, CartItemDto cartItemDto);
    void delete(Long id);
}

