package org.example.storeback.persistence.dao;

import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity;

import java.util.Optional;

public interface ProductJpaDao {
    Page<ProductJpaEntity> findAll(int page, int size);
    Optional<ProductJpaEntity> findByName(String name);
    Optional<ProductJpaEntity> findByCategory(String category);
    Optional<ProductJpaEntity> findByRating(int min, int max);
    Optional<ProductJpaEntity> findById(Long id);
    ProductJpaEntity save(ProductJpaEntity productJpaEntity);
    void deleteById(Long id);
}
