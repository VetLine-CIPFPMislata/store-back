package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.storeback.persistence.dao.OrderItemJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.OrderItemJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class OrderItemJpaDaoImpl implements OrderItemJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderItemJpaEntity> findAll() {
        TypedQuery<OrderItemJpaEntity> query = entityManager.createQuery(
                "SELECT oi FROM OrderItemJpaEntity oi", OrderItemJpaEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<OrderItemJpaEntity> findById(Long id) {
        OrderItemJpaEntity entity = entityManager.find(OrderItemJpaEntity.class, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public List<OrderItemJpaEntity> findByOrderId(Long orderId) {
        TypedQuery<OrderItemJpaEntity> query = entityManager.createQuery(
                "SELECT oi FROM OrderItemJpaEntity oi WHERE oi.order.id = :orderId",
                OrderItemJpaEntity.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    @Override
    public OrderItemJpaEntity save(OrderItemJpaEntity orderItemJpaEntity) {
        if (orderItemJpaEntity.getId() == null) {
            entityManager.persist(orderItemJpaEntity);
            entityManager.flush();
            return orderItemJpaEntity;
        } else {
            OrderItemJpaEntity merged = entityManager.merge(orderItemJpaEntity);
            entityManager.flush();
            return merged;
        }
    }

    @Override
    public void deleteById(Long id) {
        OrderItemJpaEntity entity = entityManager.find(OrderItemJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
