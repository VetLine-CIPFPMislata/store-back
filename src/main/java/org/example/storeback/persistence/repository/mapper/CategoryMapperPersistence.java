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

    public CategoryEntity fromCategoryJpaEntityToCategoryEntity(CategoryJpaEntity categoryJpaEntity) {
        if (categoryJpaEntity == null) {
            return null;
        }
        return new CategoryEntity(
                categoryJpaEntity.getId(),
                categoryJpaEntity.getName(),
                categoryJpaEntity.getDescription()
        );
    }

    public  CategoryJpaEntity fromCategoryEntityToCategoryJpaEntity(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }

        return new CategoryJpaEntity(
                categoryEntity.id(),
                categoryEntity.name(),
                categoryEntity.description()
        );
    }
}
