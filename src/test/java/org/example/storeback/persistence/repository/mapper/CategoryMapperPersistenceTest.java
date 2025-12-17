package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryMapperPersistenceTest {

    @Test
    void fromCategoryJpaEntityToCategoryEntity_mapsAllFields() {
        CategoryJpaEntity jpa = new CategoryJpaEntity(1L, "CatTest", "DescTest");

        CategoryEntity entity = CategoryMapperPersistence.getInstance().fromCategoryJpaEntityToCategoryEntity(jpa);

        assertNotNull(entity);
        assertEquals(jpa.getId(), entity.id());
        assertEquals(jpa.getName(), entity.name());
        assertEquals(jpa.getDescription(), entity.description());
    }

    @Test
    void fromCategoryEntityToCategoryJpaEntity_mapsAllFields() {
        CategoryEntity entity = new CategoryEntity(2L, "OtraCat", "OtraDesc");

        CategoryJpaEntity jpa = CategoryMapperPersistence.getInstance().fromCategoryEntityToCategoryJpaEntity(entity);

        assertNotNull(jpa);
        assertEquals(entity.id(), jpa.getId());
        assertEquals(entity.name(), jpa.getName());
        assertEquals(entity.description(), jpa.getDescription());
    }
}
