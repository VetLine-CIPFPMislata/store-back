package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.ProductEntity;
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

    public ProductEntity fromProductJpaEntityToProductEntity(ProductEntity productEntity){
        if (productEntity == null){
            return null;
        }
        return new ProductEntity(
                productEntity.id(),
                productEntity.category(),
                productEntity.name(),
                productEntity.productDescription(),
                productEntity.basePrice(),
                productEntity.discountPercentage(),
                productEntity.pictureProduct(),
                productEntity.quantity(),
                productEntity.rating()
        );
    }

    public ProductJpaEntity fromProductEntityToProductJpaEntity(ProductJpaEntity productEntity){
        if (productEntity == null){
            return  null;
        }

        return new ProductJpaEntity(
                productEntity.getId(),
                productEntity.getCategory(),
                productEntity.getName(),
                productEntity.getProductDescription(),
                productEntity.getBasePrice(),
                productEntity.getDiscountPercentage(),
                productEntity.getPictureProduct(),
                productEntity.getQuantity(),
                productEntity.getRating()
        );
    }


}
