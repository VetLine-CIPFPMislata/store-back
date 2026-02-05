package org.example.storeback.persistence.dao;

import org.example.storeback.persistence.dao.jpa.entity.CartItemJpaEntity;

import java.util.List;
import java.util.Optional;

public interface CartItemJpaDao {
    List<CartItemJpaEntity> findAll();
    Optional<CartItemJpaEntity> findById(Long id);
    List<CartItemJpaEntity> findByCartId(Long cartId);
    Optional<CartItemJpaEntity> findByCartIdAndProductId(Long cartId, Long productId);
    CartItemJpaEntity save(CartItemJpaEntity cartItemJpaEntity);
    void deleteById(Long id);
    void deleteByCartId(Long cartId);
}

