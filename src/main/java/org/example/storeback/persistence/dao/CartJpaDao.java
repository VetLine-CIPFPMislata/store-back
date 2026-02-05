package org.example.storeback.persistence.dao;

import org.example.storeback.persistence.dao.jpa.entity.CartJpaEntity;

import java.util.List;
import java.util.Optional;

public interface CartJpaDao {
    List<CartJpaEntity> findAll();
    Optional<CartJpaEntity> findById(Long id);
    Optional<CartJpaEntity> findByUserId(Long userId);
    CartJpaEntity save(CartJpaEntity cartJpaEntity);
    void deleteById(Long id);
    void removeItemFromCart(Long cartId, Long cartItemId);
}
