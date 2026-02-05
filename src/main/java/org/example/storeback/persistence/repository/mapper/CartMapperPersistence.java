package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.CartEntity;
import org.example.storeback.persistence.dao.jpa.entity.CartJpaEntity;

import java.util.Collections;
import java.util.stream.Collectors;

public class CartMapperPersistence {
    private static CartMapperPersistence instance;

    private CartMapperPersistence() {
    }

    public static CartMapperPersistence getInstance() {
        if (instance == null) {
            instance = new CartMapperPersistence();
        }
        return instance;
    }

    public CartEntity fromCartJpaEntityToCartEntity(CartJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        return new CartEntity(
                jpaEntity.getId(),
                jpaEntity.getTotalProducts(),
                jpaEntity.getTotalPrice(),
                jpaEntity.getClient() != null ? ClientMapperPersistence.getInstance().fromClientJpaEntityToClientEntity(jpaEntity.getClient()) : null,
                jpaEntity.getItems() != null
                    ? jpaEntity.getItems().stream()
                        .map(CartItemMapperPersistence.getInstance()::fromCartItemJpaEntityToCartItemEntity)
                        .collect(Collectors.toList())
                    : Collections.emptyList()
        );
    }

    public CartJpaEntity fromCartEntityToCartJpaEntity(CartEntity entity) {
        if (entity == null) {
            return null;
        }
        CartJpaEntity jpaEntity = new CartJpaEntity();
        jpaEntity.setId(entity.id());
        jpaEntity.setTotalProducts(entity.totalProducts());
        jpaEntity.setTotalPrice(entity.totalPrice());
        if (entity.user() != null) {
            jpaEntity.setClient(ClientMapperPersistence.getInstance().fromClientEntityToClientJpaEntity(entity.user()));
        }
        return jpaEntity;
    }
}
