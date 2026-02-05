package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.OrderItemEntity;
import org.example.storeback.persistence.dao.jpa.entity.OrderItemJpaEntity;

public class OrderItemMapperPersistence {
    private static OrderItemMapperPersistence instance;

    private OrderItemMapperPersistence() {
    }

    public static OrderItemMapperPersistence getInstance() {
        if (instance == null) {
            instance = new OrderItemMapperPersistence();
        }
        return instance;
    }

    public OrderItemEntity fromOrderItemJpaEntityToOrderItemEntity(OrderItemJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        return new OrderItemEntity(
                jpaEntity.getId(),
                jpaEntity.getQuantity(),
                jpaEntity.getOrder() != null ? jpaEntity.getOrder().getId() : null,
                jpaEntity.getProduct() != null ? ProductMapperPersistence.getInstance().fromProductJpaEntityToProductEntity(jpaEntity.getProduct()) : null
        );
    }

    public OrderItemJpaEntity fromOrderItemEntityToOrderItemJpaEntity(OrderItemEntity entity) {
        if (entity == null) {
            return null;
        }
        OrderItemJpaEntity jpaEntity = new OrderItemJpaEntity();
        jpaEntity.setId(entity.id());
        jpaEntity.setQuantity(entity.quantity());

        if (entity.orderId() != null) {
            org.example.storeback.persistence.dao.jpa.entity.OrderJpaEntity orderRef =
                new org.example.storeback.persistence.dao.jpa.entity.OrderJpaEntity();
            orderRef.setId(entity.orderId());
            jpaEntity.setOrder(orderRef);
        }

        if (entity.product() != null) {
            jpaEntity.setProduct(ProductMapperPersistence.getInstance().fromProductEntityToProductJpaEntity(entity.product()));
        }
        return jpaEntity;
    }
}
