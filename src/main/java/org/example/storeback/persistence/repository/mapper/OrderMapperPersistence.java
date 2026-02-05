package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.OrderEntity;
import org.example.storeback.persistence.dao.jpa.entity.OrderJpaEntity;

import java.util.Collections;
import java.util.stream.Collectors;

public class OrderMapperPersistence {
    private static OrderMapperPersistence instance;

    private OrderMapperPersistence() {
    }

    public static OrderMapperPersistence getInstance() {
        if (instance == null) {
            instance = new OrderMapperPersistence();
        }
        return instance;
    }

    public OrderEntity fromOrderJpaEntityToOrderEntity(OrderJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        return new OrderEntity(
                jpaEntity.getId(),
                jpaEntity.getTotalProducts(),
                jpaEntity.getTotalPrice(),
                jpaEntity.getState(),
                jpaEntity.getUserId() != null
                    ? new org.example.storeback.domain.repository.entity.ClientEntity(
                        jpaEntity.getUserId(), null, null, null, null, null, null)
                    : null,
                jpaEntity.getCreatedAt(),
                jpaEntity.getOrderAt(),
                jpaEntity.getAddress(),
                jpaEntity.getItems() != null
                    ? jpaEntity.getItems().stream()
                        .map(OrderItemMapperPersistence.getInstance()::fromOrderItemJpaEntityToOrderItemEntity)
                        .collect(Collectors.toList())
                    : Collections.emptyList()
        );
    }

    public OrderJpaEntity fromOrderEntityToOrderJpaEntity(OrderEntity entity) {
        if (entity == null) {
            return null;
        }
        OrderJpaEntity jpaEntity = new OrderJpaEntity();
        jpaEntity.setId(entity.id());
        jpaEntity.setTotalProducts(entity.totalProducts());
        jpaEntity.setTotalPrice(entity.totalPrice());
        jpaEntity.setState(entity.state());
        jpaEntity.setCreatedAt(entity.createdAt());
        jpaEntity.setOrderAt(entity.orderAt());
        jpaEntity.setAddress(entity.address());
        if (entity.user() != null) {
            jpaEntity.setUserId(entity.user().id());
        }
        return jpaEntity;
    }
}
