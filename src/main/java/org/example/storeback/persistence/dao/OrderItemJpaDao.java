package org.example.storeback.persistence.dao;

import org.example.storeback.persistence.dao.jpa.entity.OrderItemJpaEntity;

import java.util.List;
import java.util.Optional;

public interface OrderItemJpaDao {
    List<OrderItemJpaEntity> findAll();
    Optional<OrderItemJpaEntity> findById(Long id);
    List<OrderItemJpaEntity> findByOrderId(Long orderId);
    OrderItemJpaEntity save(OrderItemJpaEntity orderItemJpaEntity);
    void deleteById(Long id);
}
