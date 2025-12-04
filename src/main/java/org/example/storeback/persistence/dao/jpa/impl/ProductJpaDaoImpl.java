package org.example.storeback.persistence.dao.jpa.impl;

import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.ProductJpaDao;

import java.util.Optional;

public class ProductJpaDaoImpl implements ProductJpaDao {
    @Override
    public Page<ProductEntity> findAll(int page, int size) {
        return null;
    }

    @Override
    public Optional<ProductEntity> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductEntity> findByCategory(String category) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductEntity> findByRating(int min, int max) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
