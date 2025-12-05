package org.example.storeback.persistence.repository;

import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.repository.ProductRepository;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.ProductJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity;
import org.example.storeback.persistence.repository.mapper.ProductMapperPersistence;

import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaDao productJpaDao;


    public ProductRepositoryImpl(ProductJpaDao productJpaDao) {
        this.productJpaDao = productJpaDao;
    }

    @Override
    public Page<ProductEntity> findAll(int page, int size) {
        Page<ProductJpaEntity> jpaPage = productJpaDao.findAll(page, size);

        var productEntities = jpaPage.data().stream()
                .map(ProductMapperPersistence.getInstance()::fromProductJpaEntityToProductEntity)
                .toList();

        return new Page<>(
                productEntities,
                jpaPage.pageNumber(),
                jpaPage.pageSize(),
                jpaPage.totalElements()
        );
    }

    @Override
    public Optional<ProductEntity> findByName(String name) {
        return productJpaDao.findByName(name)
                .map(ProductMapperPersistence.getInstance()::fromProductJpaEntityToProductEntity);
    }

    @Override
    public Optional<ProductEntity> findByCategory(String category) {
        return productJpaDao.findByCategory(category)
                .map(ProductMapperPersistence.getInstance()::fromProductJpaEntityToProductEntity);
    }

    @Override
    public List<ProductEntity> findByRating(int min, int max) {
        return productJpaDao.findByRating(min, max);
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productJpaDao.findById(id)
                .map(ProductMapperPersistence.getInstance()::fromProductJpaEntityToProductEntity);
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        var jpaEntity = ProductMapperPersistence.getInstance()
                .fromProductEntityToProductJpaEntity(productEntity);
        var savedJpaEntity = productJpaDao.save(jpaEntity);
        return ProductMapperPersistence.getInstance()
                .fromProductJpaEntityToProductEntity(savedJpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        productJpaDao.deleteById(id);
    }
}
