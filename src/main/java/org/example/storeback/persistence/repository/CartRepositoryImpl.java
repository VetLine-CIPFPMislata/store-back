package org.example.storeback.persistence.repository;

import org.example.storeback.domain.repository.CartRepository;
import org.example.storeback.domain.repository.entity.CartEntity;
import org.example.storeback.persistence.dao.CartJpaDao;
import org.example.storeback.persistence.repository.mapper.CartMapperPersistence;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class CartRepositoryImpl implements CartRepository {

    private final CartJpaDao cartJpaDao;

    public CartRepositoryImpl(CartJpaDao cartJpaDao) {
        this.cartJpaDao = cartJpaDao;
    }

    @Override
    public List<CartEntity> findAll() {
        return cartJpaDao.findAll().stream()
                .map(CartMapperPersistence.getInstance()::fromCartJpaEntityToCartEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CartEntity> findById(Long id) {
        return cartJpaDao.findById(id)
                .map(CartMapperPersistence.getInstance()::fromCartJpaEntityToCartEntity);
    }

    @Override
    public Optional<CartEntity> findByUserId(Long userId) {
        return cartJpaDao.findByUserId(userId)
                .map(CartMapperPersistence.getInstance()::fromCartJpaEntityToCartEntity);
    }

    @Override
    public CartEntity save(CartEntity cartEntity) {
        var jpaEntity = CartMapperPersistence.getInstance()
                .fromCartEntityToCartJpaEntity(cartEntity);
        var saved = cartJpaDao.save(jpaEntity);
        return CartMapperPersistence.getInstance().fromCartJpaEntityToCartEntity(saved);
    }

    @Override
    public void deleteById(Long id) {
        cartJpaDao.deleteById(id);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long cartItemId) {
        cartJpaDao.removeItemFromCart(cartId, cartItemId);
    }

    @Override
    public void clearAllItems(Long cartId) {
        cartJpaDao.clearAllItems(cartId);
    }
}
