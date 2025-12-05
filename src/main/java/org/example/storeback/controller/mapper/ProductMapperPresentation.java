package org.example.storeback.controller.mapper;

import org.example.storeback.controller.webmodel.request.ProductInsertRequest;
import org.example.storeback.controller.webmodel.request.ProductUpdateRequest;
import org.example.storeback.controller.webmodel.response.ProductResponse;
import org.example.storeback.domain.service.dto.ProductDto;

public class ProductMapperPresentation {
    private static ProductMapperPresentation instance;

    private ProductMapperPresentation() {}

    public static ProductMapperPresentation getInstance() {
        if (instance == null) {
            instance = new ProductMapperPresentation();
        }
        return instance;
    }

    public ProductDto fromInsertRequestToDto(ProductInsertRequest request) {
        return new ProductDto(
                null,
                request.category(),
                request.name(),
                request.productDescription(),
                request.basePrice(),
                null,
                request.discountPercentage(),
                request.pictureProduct(),
                request.quantity(),
                0
        );
    }

    public ProductDto fromUpdateRequestToDto(ProductUpdateRequest request) {
        return new ProductDto(
                request.id(),
                request.category(),
                request.name(),
                request.productDescription(),
                request.basePrice(),
                null,
                request.discountPercentage(),
                request.pictureProduct(),
                request.quantity(),
                0
        );
    }

    public ProductResponse fromDtoToResponse(ProductDto dto) {
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
