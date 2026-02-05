package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.storeback.persistence.dao.OrderJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.OrderJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class OrderJpaDaoImpl implements OrderJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderJpaEntity> findAll() {
        TypedQuery<OrderJpaEntity> query = entityManager.createQuery(
                "SELECT o FROM OrderJpaEntity o LEFT JOIN FETCH o.items", OrderJpaEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<OrderJpaEntity> findById(Long id) {
        TypedQuery<OrderJpaEntity> query = entityManager.createQuery(
                "SELECT o FROM OrderJpaEntity o LEFT JOIN FETCH o.items WHERE o.id = :id",
                OrderJpaEntity.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<OrderJpaEntity> findByUserId(Long userId) {
        TypedQuery<OrderJpaEntity> query = entityManager.createQuery(
                "SELECT o FROM OrderJpaEntity o LEFT JOIN FETCH o.items WHERE o.user.id = :userId",
                OrderJpaEntity.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public OrderJpaEntity save(OrderJpaEntity orderJpaEntity) {
        if (orderJpaEntity.getId() == null) {
            entityManager.persist(orderJpaEntity);
            entityManager.flush();
            return orderJpaEntity;
        } else {
            OrderJpaEntity merged = entityManager.merge(orderJpaEntity);
            entityManager.flush();
            return merged;
        }
    }

    @Override
    public void deleteById(Long id) {
        OrderJpaEntity entity = entityManager.find(OrderJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
