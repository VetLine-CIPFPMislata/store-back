package org.example.storeback.persistence.dao;

import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.repository.entity.ProductEntity;

import java.util.Optional;

public interface ProductJpaDao {
    Page<ProductEntity> findAll(int page, int size);
    Optional<ProductEntity> findByName(String name);
    Optional<ProductEntity> findByCategory(String category);
    Optional<ProductEntity> findByRating(int min, int max);
    Optional<ProductEntity> findById(Long id);
    ProductEntity save(ProductEntity productEntity);
    void deleteById(Long id);
}
