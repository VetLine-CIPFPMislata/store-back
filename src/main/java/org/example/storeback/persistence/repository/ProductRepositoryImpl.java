package org.example.storeback.persistence.repository;

import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.repository.ProductRepository;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.ProductJpaDao;

import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaDao productJpaDao;
    public ProductRepositoryImpl(ProductJpaDao productJpaDao) {
        this.productJpaDao = productJpaDao;
    }

    @Override
    public Page<ProductEntity> findAll(int page, int size) {
        return productJpaDao.findAll(page, size);
    }

    @Override
    public Optional<ProductEntity> findByName(String name) {
        return productJpaDao.findByName(name);
    }

    @Override
    public Optional<ProductEntity> findByCategory(String category) {
        return productJpaDao.findByCategory(category);
    }

    @Override
    public Optional<ProductEntity> findByRating(int min, int max) {
        return productJpaDao.findByRating(min, max);
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productJpaDao.findById(id);
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productJpaDao.save(productEntity);
    }

    @Override
    public void deleteById(Long id) {
        productJpaDao.deleteById(id);
    }
}
