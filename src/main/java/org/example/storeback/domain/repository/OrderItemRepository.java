package org.example.storeback.domain.repository;

import org.example.storeback.domain.repository.entity.OrderItemEntity;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository {
    List<OrderItemEntity> findAll();
    Optional<OrderItemEntity> findById(Long id);
    List<OrderItemEntity> findByOrderId(Long orderId);
    OrderItemEntity save(OrderItemEntity orderItemEntity);
    void deleteById(Long id);
}

