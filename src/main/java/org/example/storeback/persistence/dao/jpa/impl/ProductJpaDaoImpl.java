package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.ProductJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity;
import org.example.storeback.persistence.repository.mapper.ProductMapperPersistence;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class ProductJpaDaoImpl implements ProductJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ProductJpaEntity> findAll(int page, int size) {
        TypedQuery<ProductJpaEntity> query = entityManager.createQuery(
                "SELECT p FROM ProductJpaEntity p", ProductJpaEntity.class
        );

        long totalElements = entityManager.createQuery(
                "SELECT COUNT(p) FROM ProductJpaEntity p", Long.class
        ).getSingleResult();

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        List<ProductJpaEntity> items = query.getResultList();

        return new Page<>(items, page, size, totalElements);
    }

    @Override
    public Optional<ProductJpaEntity> findByName(String name) {
        TypedQuery<ProductJpaEntity> query = entityManager.createQuery(
                "SELECT p FROM ProductJpaEntity p WHERE p.name = :name",
                ProductJpaEntity.class
        );
        query.setParameter("name", name);

        return query.getResultStream().findFirst();
    }

    @Override
    public Optional<ProductJpaEntity> findByCategory(String category) {
        TypedQuery<ProductJpaEntity> query = entityManager.createQuery(
                "SELECT p FROM ProductJpaEntity p WHERE p.category.name = :category",
                ProductJpaEntity.class
        );
        query.setParameter("category", category);

        return query.getResultStream().findFirst();
    }

    @Override
    public Optional<ProductJpaEntity> findByRating(int min, int max) {
        TypedQuery<ProductJpaEntity> query = entityManager.createQuery(
                "SELECT p FROM ProductJpaEntity p WHERE p.rating BETWEEN :min AND :max",
                ProductJpaEntity.class
        );
        query.setParameter("min", min);
        query.setParameter("max", max);

        return query.getResultStream().findFirst();
    }

    @Override
    public Optional<ProductJpaEntity> findById(Long id) {
        ProductJpaEntity entity = entityManager.find(ProductJpaEntity.class, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public ProductJpaEntity save(ProductJpaEntity productJpaEntity) {
        if (productJpaEntity.getId() == null) {
            entityManager.persist(productJpaEntity);
            return productJpaEntity;
        } else {
            return entityManager.merge(productJpaEntity);
        }
    }

    @Override
    public void deleteById(Long id) {
        ProductJpaEntity entity = entityManager.find(ProductJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
