package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.models.Category;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity;

public class ProductMapperPersistence {

    private static ProductMapperPersistence INSTANCE;

    private ProductMapperPersistence() {
    }

    public static ProductMapperPersistence getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductMapperPersistence();
        }
        return INSTANCE;
    }

    public ProductEntity fromProductJpaEntityToProductEntity(ProductJpaEntity productJpaEntity){
        if (productJpaEntity == null){
            return null;
        }
        Category category = new Category(
                productJpaEntity.getCategory().getId(),
                productJpaEntity.getCategory().getName(),
                productJpaEntity.getCategory().getDescription()
        );
        return new ProductEntity(
                productJpaEntity.getId(),
                category,
                productJpaEntity.getName(),
                productJpaEntity.getProductDescription(),
                productJpaEntity.getBasePrice(),
                productJpaEntity.getDiscountPercentage(),
                productJpaEntity.getPictureProduct(),
                productJpaEntity.getQuantity(),
                productJpaEntity.getRating()
        );
    }

    public ProductJpaEntity fromProductEntityToProductJpaEntity(ProductEntity productEntity){
        if (productEntity == null){
            return  null;
        }

        CategoryJpaEntity categoryJpaEntity = new CategoryJpaEntity(
                productEntity.category().getId(),
                productEntity.category().getName(),
                productEntity.category().getDescription()
        );

        return new ProductJpaEntity(
                productEntity.id(),
                categoryJpaEntity,
                productEntity.name(),
                productEntity.productDescription(),
                productEntity.basePrice(),
                productEntity.discountPercentage(),
                productEntity.pictureProduct(),
                productEntity.quantity(),
                productEntity.rating()
        );
    }


}
