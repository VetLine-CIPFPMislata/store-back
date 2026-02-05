package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.storeback.persistence.dao.CartJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.CartJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class CartJpaDaoImpl implements CartJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CartJpaEntity> findAll() {
        TypedQuery<CartJpaEntity> query = entityManager.createQuery(
                "SELECT c FROM CartJpaEntity c LEFT JOIN FETCH c.items", CartJpaEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<CartJpaEntity> findById(Long id) {
        CartJpaEntity entity = entityManager.find(CartJpaEntity.class, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<CartJpaEntity> findByUserId(Long userId) {
        TypedQuery<CartJpaEntity> query = entityManager.createQuery(
                "SELECT c FROM CartJpaEntity c LEFT JOIN FETCH c.items WHERE c.client.id = :userId",
                CartJpaEntity.class);
        query.setParameter("userId", userId);
        return query.getResultStream().findFirst();
    }

    @Override
    public CartJpaEntity save(CartJpaEntity cartJpaEntity) {
        if (cartJpaEntity.getId() == null) {
            entityManager.persist(cartJpaEntity);
            entityManager.flush();
            return cartJpaEntity;
        } else {
            CartJpaEntity existingEntity = entityManager.find(CartJpaEntity.class, cartJpaEntity.getId());
            if (existingEntity == null) {
                throw new RuntimeException("Cart not found with id: " + cartJpaEntity.getId());
            }

            existingEntity.setTotalProducts(cartJpaEntity.getTotalProducts());
            existingEntity.setTotalPrice(cartJpaEntity.getTotalPrice());

            return existingEntity;
        }
    }

    @Override
    public void deleteById(Long id) {
        CartJpaEntity entity = entityManager.find(CartJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public void removeItemFromCart(Long cartId, Long cartItemId) {
        CartJpaEntity cart = entityManager.find(CartJpaEntity.class, cartId);
        if (cart == null) {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }
        boolean removed = cart.getItems().removeIf(item -> item.getId().equals(cartItemId));

        if (!removed) {
            throw new RuntimeException("Cart item not found with id: " + cartItemId);
        }

    }
}
