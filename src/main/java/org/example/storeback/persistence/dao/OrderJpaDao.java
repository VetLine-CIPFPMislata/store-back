package org.example.storeback.persistence.dao;

import org.example.storeback.persistence.dao.jpa.entity.OrderJpaEntity;

import java.util.List;
import java.util.Optional;

public interface OrderJpaDao {
    List<OrderJpaEntity> findAll();
    Optional<OrderJpaEntity> findById(Long id);
    List<OrderJpaEntity> findByUserId(Long userId);
    OrderJpaEntity save(OrderJpaEntity orderJpaEntity);
    void deleteById(Long id);
}

