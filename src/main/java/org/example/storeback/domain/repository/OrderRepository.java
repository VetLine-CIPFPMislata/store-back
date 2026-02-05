package org.example.storeback.domain.repository;

import org.example.storeback.domain.repository.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<OrderEntity> findAll();
    Optional<OrderEntity> findById(Long id);
    List<OrderEntity> findByUserId(Long userId);
    OrderEntity save(OrderEntity orderEntity);
    void deleteById(Long id);
}

