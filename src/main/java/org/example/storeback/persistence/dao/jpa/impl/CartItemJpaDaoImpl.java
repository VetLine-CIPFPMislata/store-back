package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.storeback.persistence.dao.CartItemJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.CartItemJpaEntity;
import org.example.storeback.persistence.dao.jpa.entity.CartJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class CartItemJpaDaoImpl implements CartItemJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CartItemJpaEntity> findAll() {
        TypedQuery<CartItemJpaEntity> query = entityManager.createQuery(
                "SELECT ci FROM CartItemJpaEntity ci", CartItemJpaEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<CartItemJpaEntity> findById(Long id) {
        CartItemJpaEntity entity = entityManager.find(CartItemJpaEntity.class, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public List<CartItemJpaEntity> findByCartId(Long cartId) {
        TypedQuery<CartItemJpaEntity> query = entityManager.createQuery(
                "SELECT ci FROM CartItemJpaEntity ci WHERE ci.cart.id = :cartId",
                CartItemJpaEntity.class);
        query.setParameter("cartId", cartId);
        return query.getResultList();
    }

    @Override
    public Optional<CartItemJpaEntity> findByCartIdAndProductId(Long cartId, Long productId) {
        TypedQuery<CartItemJpaEntity> query = entityManager.createQuery(
                "SELECT ci FROM CartItemJpaEntity ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId",
                CartItemJpaEntity.class);
        query.setParameter("cartId", cartId);
        query.setParameter("productId", productId);
        return query.getResultStream().findFirst();
    }

    @Override
    public CartItemJpaEntity save(CartItemJpaEntity cartItemJpaEntity) {
        // Obtener referencia real del carrito desde el EntityManager
        if (cartItemJpaEntity.getCart() != null && cartItemJpaEntity.getCart().getId() != null) {
            CartJpaEntity cartRef = entityManager.getReference(
                    CartJpaEntity.class,
                    cartItemJpaEntity.getCart().getId()
            );
            cartItemJpaEntity.setCart(cartRef);
        }

        if (cartItemJpaEntity.getId() == null) {
            entityManager.persist(cartItemJpaEntity);
            entityManager.flush();
            return cartItemJpaEntity;
        } else {

            CartItemJpaEntity merged = entityManager.merge(cartItemJpaEntity);
            entityManager.flush();
            return merged;
        }
    }

    @Override
    public void deleteById(Long id) {
        CartItemJpaEntity entity = entityManager.find(CartItemJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
            entityManager.flush();
        }
    }

    @Override
    public void deleteByCartId(Long cartId) {
        entityManager.createQuery("DELETE FROM CartItemJpaEntity ci WHERE ci.cart.id = :cartId")
                .setParameter("cartId", cartId)
                .executeUpdate();
    }
}
