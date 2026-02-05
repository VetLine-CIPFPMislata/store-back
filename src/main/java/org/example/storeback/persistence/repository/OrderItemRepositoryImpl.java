package org.example.storeback.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.storeback.domain.repository.OrderItemRepository;
import org.example.storeback.domain.repository.entity.OrderItemEntity;
import org.example.storeback.persistence.dao.OrderItemJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.OrderItemJpaEntity;
import org.example.storeback.persistence.dao.jpa.entity.OrderJpaEntity;
import org.example.storeback.persistence.repository.mapper.OrderItemMapperPersistence;
import org.example.storeback.persistence.repository.mapper.ProductMapperPersistence;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final OrderItemJpaDao orderItemJpaDao;

    @PersistenceContext
    private EntityManager entityManager;

    public OrderItemRepositoryImpl(OrderItemJpaDao orderItemJpaDao) {
        this.orderItemJpaDao = orderItemJpaDao;
    }

    @Override
    public List<OrderItemEntity> findAll() {
        return orderItemJpaDao.findAll().stream()
                .map(OrderItemMapperPersistence.getInstance()::fromOrderItemJpaEntityToOrderItemEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderItemEntity> findById(Long id) {
        return orderItemJpaDao.findById(id)
                .map(OrderItemMapperPersistence.getInstance()::fromOrderItemJpaEntityToOrderItemEntity);
    }

    @Override
    public List<OrderItemEntity> findByOrderId(Long orderId) {
        return orderItemJpaDao.findByOrderId(orderId).stream()
                .map(OrderItemMapperPersistence.getInstance()::fromOrderItemJpaEntityToOrderItemEntity)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemEntity save(OrderItemEntity orderItemEntity) {
        OrderItemJpaEntity jpaEntity = new OrderItemJpaEntity();
        jpaEntity.setId(orderItemEntity.id());
        jpaEntity.setQuantity(orderItemEntity.quantity());

        if (orderItemEntity.orderId() != null) {
            OrderJpaEntity orderRef = entityManager.getReference(OrderJpaEntity.class, orderItemEntity.orderId());
            jpaEntity.setOrder(orderRef);
        }

        if (orderItemEntity.product() != null && orderItemEntity.product().id() != null) {
            var productRef = entityManager.getReference(
                org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity.class,
                orderItemEntity.product().id()
            );
            jpaEntity.setProduct(productRef);
        }

        var saved = orderItemJpaDao.save(jpaEntity);
        return OrderItemMapperPersistence.getInstance().fromOrderItemJpaEntityToOrderItemEntity(saved);
    }

    @Override
    public void deleteById(Long id) {
        orderItemJpaDao.deleteById(id);
    }
}
