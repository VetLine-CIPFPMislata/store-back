package org.example.storeback.controller.mapper;

import org.example.storeback.controller.webmodel.request.CategoryInsertRequest;
import org.example.storeback.controller.webmodel.response.CategoryResponse;

import org.example.storeback.domain.service.dto.CategoryDto;

public class CategoryMapperPresentation {

    private static CategoryMapperPresentation INSTANCE;

    public static CategoryMapperPresentation getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryMapperPresentation();
        }
        return INSTANCE;
    }
    public static CategoryResponse fromCategoryDtoToCategoryResponse(CategoryDto categoryDto){
        if (categoryDto == null){
            return null;
        }
        return new CategoryResponse(
                categoryDto.id(),
                categoryDto.name()
        );
    }
    public static CategoryDto fromCategoryInsertToCategoryDto(CategoryInsertRequest categoryInsert){
        if (categoryInsert == null){
            return null;
        }
        return new CategoryDto(
            null,
                categoryInsert.name(),
                categoryInsert.description()
        );
    }
}
