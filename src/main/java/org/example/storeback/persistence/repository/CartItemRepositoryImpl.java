package org.example.storeback.persistence.repository;

import org.example.storeback.domain.repository.CartItemRepository;
import org.example.storeback.domain.repository.entity.CartItemEntity;
import org.example.storeback.persistence.dao.CartItemJpaDao;
import org.example.storeback.persistence.repository.mapper.CartItemMapperPersistence;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class CartItemRepositoryImpl implements CartItemRepository {

    private final CartItemJpaDao cartItemJpaDao;

    public CartItemRepositoryImpl(CartItemJpaDao cartItemJpaDao) {
        this.cartItemJpaDao = cartItemJpaDao;
    }

    @Override
    public List<CartItemEntity> findAll() {
        return cartItemJpaDao.findAll().stream()
                .map(CartItemMapperPersistence.getInstance()::fromCartItemJpaEntityToCartItemEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CartItemEntity> findById(Long id) {
        return cartItemJpaDao.findById(id)
                .map(CartItemMapperPersistence.getInstance()::fromCartItemJpaEntityToCartItemEntity);
    }

    @Override
    public List<CartItemEntity> findByCartId(Long cartId) {
        return cartItemJpaDao.findByCartId(cartId).stream()
                .map(CartItemMapperPersistence.getInstance()::fromCartItemJpaEntityToCartItemEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId) {
        return cartItemJpaDao.findByCartIdAndProductId(cartId, productId)
                .map(CartItemMapperPersistence.getInstance()::fromCartItemJpaEntityToCartItemEntity);
    }

    @Override
    public CartItemEntity save(CartItemEntity cartItemEntity) {
        var cartItemJpaEntity = CartItemMapperPersistence.getInstance()
                .fromCartItemEntityToCartItemJpaEntity(cartItemEntity);
        var saved = cartItemJpaDao.save(cartItemJpaEntity);
        return CartItemMapperPersistence.getInstance().fromCartItemJpaEntityToCartItemEntity(saved);
    }

    @Override
    public void deleteById(Long id) {
        cartItemJpaDao.deleteById(id);
    }

    @Override
    public void deleteByCartId(Long cartId) {
        cartItemJpaDao.deleteByCartId(cartId);
    }
}
