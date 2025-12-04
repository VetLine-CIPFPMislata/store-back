package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.persistence.dao.jpa.entity.CategoryJpaEntity;

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

    public CategoryJpaEntity fromCategoryJpaEntityToCategoryEntity(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        return new CategoryJpaEntity(
                categoryEntity.id(),
                categoryEntity.name(),
                categoryEntity.description()
        );
    }

    public  CategoryJpaEntity fromCategoryEntityToCategoryJpaEntity(CategoryJpaEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }

        return new CategoryJpaEntity(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getDescription()
        );
    }
}
