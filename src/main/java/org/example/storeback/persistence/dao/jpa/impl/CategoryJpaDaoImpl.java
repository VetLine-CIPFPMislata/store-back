package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.persistence.dao.CategoryJpaDao;

import java.util.List;
import java.util.Optional;

@Transactional
public class CategoryJpaDaoImpl implements CategoryJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CategoryEntity> findAll() {
        String jpql = "SELECT c FROM CategoryJpaEntity c";
        return entityManager.createQuery(jpql, CategoryEntity.class)
                .getResultList();
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        CategoryEntity categoryEntity = entityManager.find(CategoryEntity.class, id);
        return Optional.ofNullable(categoryEntity);
    }

    @Override
    public Optional<CategoryEntity> findByName(String name) {
        String jpql = "SELECT c FROM CategoryJpaEntity c WHERE c.name = :name";
        return entityManager.createQuery(jpql, CategoryEntity.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        if (categoryEntity.id() == null) {
            entityManager.persist(categoryEntity);
            return categoryEntity;
        } else {
            return entityManager.merge(categoryEntity);
        }
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }
}
