package org.example.storeback.domain.mappers;

import org.example.storeback.domain.models.Category;
import org.example.storeback.domain.repository.entity.CategoryEntity;

public class CategoryMapper {
    private static CategoryMapper instance;

    private CategoryMapper() {
    }

    public static CategoryMapper getInstance() {
        if (instance == null) {
            instance = new CategoryMapper();
        }
        return instance;
    }

    public Category fromCategoryEntityToCategory(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            throw new IllegalArgumentException("CategoryEntity cannot be null");
        }
        return new Category(
                categoryEntity.id(),
                categoryEntity.name(),
                categoryEntity.description()
        );
    }

    public CategoryEntity fromCategoryToCategoryEntity(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        return new CategoryEntity(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public Category fromCategoryDtoToCategory(CategoryEntity categoryDto) {
        if (categoryDto == null) {
            throw new IllegalArgumentException("CategoryDto cannot be null");
        }
        return new Category(
                categoryDto.id(),
                categoryDto.name(),
                categoryDto.description()
        );
    }

    public CategoryEntity fromCategoryToCategoryDto(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        return new CategoryEntity(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
