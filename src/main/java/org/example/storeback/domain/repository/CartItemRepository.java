package org.example.storeback.domain.repository;

import org.example.storeback.domain.repository.entity.CartItemEntity;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository {
    List<CartItemEntity> findAll();
    Optional<CartItemEntity> findById(Long id);
    List<CartItemEntity> findByCartId(Long cartId);
    Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);
    CartItemEntity save(CartItemEntity cartItemEntity);
    void deleteById(Long id);
    void deleteByCartId(Long cartId);
}
