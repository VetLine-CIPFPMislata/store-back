// java
package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.persistence.dao.CategoryJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.example.storeback.persistence.repository.mapper.CategoryMapperPersistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public class CategoryJpaDaoImpl implements CategoryJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final CategoryMapperPersistence mapper = CategoryMapperPersistence.getInstance();

    @Override
    public List<CategoryEntity> findAll() {
        String jpql = "SELECT c FROM CategoryJpaEntity c";
        List<CategoryJpaEntity> jpaList = entityManager.createQuery(jpql, CategoryJpaEntity.class)
                .getResultList();
        return jpaList.stream()
                .map(mapper::fromCategoryJpaEntityToCategoryEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        CategoryJpaEntity jpa = entityManager.find(CategoryJpaEntity.class, id);
        return Optional.ofNullable(mapper.fromCategoryJpaEntityToCategoryEntity(jpa));
    }

    @Override
    public Optional<CategoryEntity> findByName(String name) {
        String jpql = "SELECT c FROM CategoryJpaEntity c WHERE c.name = :name";
        List<CategoryJpaEntity> result = entityManager.createQuery(jpql, CategoryJpaEntity.class)
                .setParameter("name", name)
                .getResultStream()
                .toList();
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(mapper.fromCategoryJpaEntityToCategoryEntity(result.get(0)));
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        CategoryJpaEntity jpa = mapper.fromCategoryEntityToCategoryJpaEntity(categoryEntity);
        if (categoryEntity.id() == null) {
            entityManager.persist(jpa);
            entityManager.flush();
            return mapper.fromCategoryJpaEntityToCategoryEntity(jpa);
        } else {
            CategoryJpaEntity merged = entityManager.merge(jpa);
            return mapper.fromCategoryJpaEntityToCategoryEntity(merged);
        }
    }

    @Override
    public void deleteById(Long id) {
        CategoryJpaEntity jpa = entityManager.find(CategoryJpaEntity.class, id);
        if (jpa != null) {
            entityManager.remove(jpa);
        }
    }
}
