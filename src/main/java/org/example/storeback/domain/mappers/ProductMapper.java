package org.example.storeback.domain.mappers;

import org.example.storeback.domain.models.Product;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.domain.service.dto.ProductDto;

public class ProductMapper {
    private static ProductMapper instance;

    private ProductMapper() {
    }

    public static ProductMapper getInstance() {
        if (instance == null) {
            instance = new ProductMapper();
        }
        return instance;
    }

    public ProductDto fromProductToProductDto(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        return new ProductDto(
                product.getId(),
                product.getCategory(),
                product.getName(),
                product.getProductDescription(),
                product.getBaseprice(),
                product.getPrice(),
                product.getDiscountPercentage(),
                product.getPictureProduct(),
                product.getQuantity(),
                product.getRating()
        );
    }

    public Product fromProductDtoToProduct(ProductDto productDto) {
        if (productDto == null) {
            throw new IllegalArgumentException("ProductDto cannot be null");
        }
        return new Product(
                productDto.id(),
                productDto.category(),
                productDto.name(),
                productDto.productDescription(),
                productDto.basePrice(),
                productDto.discountPercentage(),
                productDto.pictureProduct(),
                productDto.quantity(),
                productDto.rating()
        );
    }

    public Product fromProductEntityToProduct(ProductEntity productEntity) {
        if (productEntity == null) {
            throw new IllegalArgumentException("ProductEntity cannot be null");
        }
        return new Product(
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

    public ProductEntity fromProductToProductEntity(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        return new ProductEntity(
                product.getId(),
                product.getCategory(),
                product.getName(),
                product.getProductDescription(),
                product.getBaseprice(),
                product.getDiscountPercentage(),
                product.getPictureProduct(),
                product.getQuantity(),
                product.getRating()
        );
    }

}
