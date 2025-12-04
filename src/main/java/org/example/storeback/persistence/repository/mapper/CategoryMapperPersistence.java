package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.CategoryEntity;

public class CategoryMapperPersistence {

    private static  CategoryMapperPersistence INSTANCE;
    public static CategoryMapperPersistence getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryMapperPersistence();
        }
        return  INSTANCE;
    }
    public  CategoryMapperPersistence() {
    }

    public CategoryEntity fromCategoryJpaEntityToCategoryEntity(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        return new CategoryEntity(
                categoryEntity.id(),
                categoryEntity.name(),
                categoryEntity.description()
        );
    }

    public  CategoryEntity fromCategoryEntityToCategoryEntity(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }

        return new CategoryEntity(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getDescription()
        );
    }
}
