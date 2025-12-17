package org.example.storeback.testutil;

import org.example.storeback.controller.webmodel.request.ProductInsertRequest;
import org.example.storeback.controller.webmodel.request.ProductUpdateRequest;
import org.example.storeback.controller.webmodel.response.ProductResponse;
import org.example.storeback.domain.models.Category;
import org.example.storeback.domain.models.Product;
import org.example.storeback.domain.service.dto.ProductDto;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity;

import java.math.BigDecimal;

public class ProductFixtures {

    public static Category sampleCategory() {
        return new Category(1L, "Cat1", "Description");
    }

    public static ProductDto sampleProductDto() {
        return new ProductDto(
                1L,
                sampleCategory(),
                "Product 1",
                "Desc",
                new BigDecimal("10.00"),
                new BigDecimal("12.00"),
                new BigDecimal("5.00"),
                "http://img",
                5,
                4
        );
    }

    public static ProductEntity sampleProductEntity() {
        Category cat = sampleCategory();
        return new ProductEntity(
                1L,
                cat,
                "Product 1",
                "Desc",
                new BigDecimal("10.00"),
                new BigDecimal("5.00"),
                "http://img",
                5,
                4
        );
    }

    public static Product sampleProductDomain() {
        ProductDto dto = sampleProductDto();
        return new Product(
                dto.id(),
                dto.category(),
                dto.name(),
                dto.productDescription(),
                dto.basePrice(),
                dto.discountPercentage(),
                dto.pictureProduct(),
                dto.quantity(),
                dto.rating()
        );
    }

    public static ProductJpaEntity sampleProductJpaEntity() {
        CategoryJpaEntity categoryJpa = new CategoryJpaEntity(1L, "Cat1", "Description");
        return new ProductJpaEntity(
                1L,
                categoryJpa,
                "Product 1",
                "Desc",
                new BigDecimal("10.00"),
                new BigDecimal("5.00"),
                "http://img",
                5,
                4
        );
    }

    public static ProductInsertRequest sampleInsertRequest() {
        return new ProductInsertRequest(
                "Product 1",
                "Desc",
                sampleCategory(),
                "http://img",
                5,
                new BigDecimal("10.00"),
                new BigDecimal("5.00")
        );
    }

    public static ProductUpdateRequest sampleUpdateRequest() {
        return new ProductUpdateRequest(
                1L,
                "Product 1",
                "Desc",
                sampleCategory(),
                "http://img",
                5,
                new BigDecimal("10.00"),
                new BigDecimal("5.00")
        );
    }

    public static ProductResponse sampleResponse() {
        ProductDto dto = sampleProductDto();
        return new ProductResponse(
                dto.id(),
                dto.name(),
                dto.productDescription(),
                dto.category(),
                dto.pictureProduct(),
                dto.quantity(),
                dto.basePrice(),
                dto.discountPercentage(),
                dto.price()
        );
    }

}
