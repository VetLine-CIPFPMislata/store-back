package org.example.storeback.persistence.repository;

import org.example.storeback.domain.repository.CategoryRepository;
import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.persistence.dao.CategoryJpaDao;

import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaDao categoryJpaDao;

    public CategoryRepositoryImpl(CategoryJpaDao categoryJpaDao) {
        this.categoryJpaDao = categoryJpaDao;
    }

    @Override
    public List<CategoryEntity> findAll() {
        return categoryJpaDao.findAll();
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        return categoryJpaDao.findById(id);
    }

    @Override
    public Optional<CategoryEntity> findByName(String name) {
        return categoryJpaDao.findByName(name);
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        return categoryJpaDao.save(categoryEntity);
    }

    @Override
    public void deleteById(Long id) {
        categoryJpaDao.deleteById(id);
    }
}
