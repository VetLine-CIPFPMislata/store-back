package org.example.storeback.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.repository.ProductRepository;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.ProductJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity;
import org.example.storeback.persistence.repository.mapper.ProductMapperPersistence;

import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaDao productJpaDao;

    @PersistenceContext
    private EntityManager entityManager;

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
    public List<ProductEntity> findByCategory(String category) {
        return productJpaDao.findByCategory(category).stream()
                .map(ProductMapperPersistence.getInstance()::fromProductJpaEntityToProductEntity)
                .toList();
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
        ProductJpaEntity jpaEntity = new ProductJpaEntity();
        jpaEntity.setId(productEntity.id());
        jpaEntity.setName(productEntity.name());
        jpaEntity.setProductDescription(productEntity.productDescription());
        jpaEntity.setBasePrice(productEntity.basePrice());
        jpaEntity.setDiscountPercentage(productEntity.discountPercentage());
        jpaEntity.setPictureProduct(productEntity.pictureProduct());
        jpaEntity.setQuantity(productEntity.quantity());
        jpaEntity.setRating(productEntity.rating());

        // Usar getReference en lugar de crear una nueva instancia
        if (productEntity.category() != null && productEntity.category().getId() != null) {
            CategoryJpaEntity categoryRef = entityManager.getReference(CategoryJpaEntity.class, productEntity.category().getId());
            jpaEntity.setCategory(categoryRef);
        }

        var savedJpaEntity = productJpaDao.save(jpaEntity);
        return ProductMapperPersistence.getInstance()
                .fromProductJpaEntityToProductEntity(savedJpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        productJpaDao.deleteById(id);
    }
}
