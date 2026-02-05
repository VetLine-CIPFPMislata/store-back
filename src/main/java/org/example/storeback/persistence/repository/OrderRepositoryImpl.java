package org.example.storeback.persistence.repository;

import org.example.storeback.domain.repository.OrderRepository;
import org.example.storeback.domain.repository.entity.OrderEntity;
import org.example.storeback.persistence.dao.OrderJpaDao;
import org.example.storeback.persistence.repository.mapper.OrderMapperPersistence;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaDao orderJpaDao;

    public OrderRepositoryImpl(OrderJpaDao orderJpaDao) {
        this.orderJpaDao = orderJpaDao;
    }

    @Override
    public List<OrderEntity> findAll() {
        return orderJpaDao.findAll().stream()
                .map(OrderMapperPersistence.getInstance()::fromOrderJpaEntityToOrderEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderEntity> findById(Long id) {
        return orderJpaDao.findById(id)
                .map(OrderMapperPersistence.getInstance()::fromOrderJpaEntityToOrderEntity);
    }

    @Override
    public List<OrderEntity> findByUserId(Long userId) {
        return orderJpaDao.findByUserId(userId).stream()
                .map(OrderMapperPersistence.getInstance()::fromOrderJpaEntityToOrderEntity)
                .collect(Collectors.toList());
    }

    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        var orderJpaEntity = OrderMapperPersistence.getInstance()
                .fromOrderEntityToOrderJpaEntity(orderEntity);
        var saved = orderJpaDao.save(orderJpaEntity);
        return OrderMapperPersistence.getInstance().fromOrderJpaEntityToOrderEntity(saved);
    }

    @Override
    public void deleteById(Long id) {
        orderJpaDao.deleteById(id);
    }
}