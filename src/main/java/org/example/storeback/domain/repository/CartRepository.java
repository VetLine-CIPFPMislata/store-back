package org.example.storeback.domain.repository;

import org.example.storeback.domain.repository.entity.CartEntity;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    List<CartEntity> findAll();
    Optional<CartEntity> findById(Long id);
    Optional<CartEntity> findByUserId(Long userId);
    CartEntity save(CartEntity cartEntity);
    void deleteById(Long id);
    void removeItemFromCart(Long cartId, Long cartItemId);
    void clearAllItems(Long cartId);
}
