package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.CartItemEntity;
import org.example.storeback.persistence.dao.jpa.entity.CartItemJpaEntity;
import org.example.storeback.persistence.dao.jpa.entity.CartJpaEntity;

public class CartItemMapperPersistence {
    private static CartItemMapperPersistence instance;

    private CartItemMapperPersistence() {
    }

    public static CartItemMapperPersistence getInstance() {
        if (instance == null) {
            instance = new CartItemMapperPersistence();
        }
        return instance;
    }

    public CartItemEntity fromCartItemJpaEntityToCartItemEntity(CartItemJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        return new CartItemEntity(
                jpaEntity.getId(),
                jpaEntity.getQuantity(),
                jpaEntity.getCart() != null ? jpaEntity.getCart().getId() : null,
                jpaEntity.getProduct() != null ? ProductMapperPersistence.getInstance().fromProductJpaEntityToProductEntity(jpaEntity.getProduct()) : null,
                jpaEntity.getUnitPrice()
        );
    }

    public CartItemJpaEntity fromCartItemEntityToCartItemJpaEntity(CartItemEntity entity) {
        if (entity == null) {
            return null;
        }
        CartItemJpaEntity jpaEntity = new CartItemJpaEntity();
        jpaEntity.setId(entity.id());
        jpaEntity.setQuantity(entity.quantity());
        jpaEntity.setUnitPrice(entity.unitPrice());


        if (entity.cartId() != null) {
            CartJpaEntity cart = new CartJpaEntity();
            cart.setId(entity.cartId());
            jpaEntity.setCart(cart);
        }

        if (entity.product() != null) {
            jpaEntity.setProduct(ProductMapperPersistence.getInstance().fromProductEntityToProductJpaEntity(entity.product()));
        }
        return jpaEntity;
    }
}
