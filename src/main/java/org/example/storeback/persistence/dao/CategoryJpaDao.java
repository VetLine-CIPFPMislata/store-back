package org.example.storeback.persistence.dao;

import org.example.storeback.domain.repository.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryJpaDao {
    List<CategoryEntity> findAll();
    Optional<CategoryEntity> findById(Long id);
    Optional<CategoryEntity> findByName(String name);
    CategoryEntity save(CategoryEntity categoryEntity);
    void deleteById(Long id);
}