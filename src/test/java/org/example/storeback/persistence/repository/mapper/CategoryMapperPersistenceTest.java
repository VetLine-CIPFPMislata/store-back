package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CategoryMapperPersistence Tests")
public class CategoryMapperPersistenceTest {

    private final CategoryMapperPersistence mapper = CategoryMapperPersistence.getInstance();

    @Test
    @DisplayName("fromCategoryJpaEntityToCategoryEntity - Debe mapear correctamente de JPA a Entity")
    void jpa_to_category_entity() {
        CategoryJpaEntity jpa = new CategoryJpaEntity(1L, "Electrónica", "Dispositivos electrónicos");

        CategoryEntity entity = mapper.fromCategoryJpaEntityToCategoryEntity(jpa);

        assertNotNull(entity);
        assertEquals(1L, entity.id());
        assertEquals("Electrónica", entity.name());
        assertEquals("Dispositivos electrónicos", entity.description());
    }

    @Test
    @DisplayName("fromCategoryEntityToCategoryJpaEntity - Debe mapear correctamente de Entity a JPA")
    void category_entity_to_jpa() {
        CategoryEntity entity = new CategoryEntity(1L, "Electrónica", "Dispositivos electrónicos");

        CategoryJpaEntity jpa = mapper.fromCategoryEntityToCategoryJpaEntity(entity);

        assertNotNull(jpa);
        assertEquals(1L, jpa.getId());
        assertEquals("Electrónica", jpa.getName());
        assertEquals("Dispositivos electrónicos", jpa.getDescription());
    }

    @Test
    @DisplayName("fromCategoryJpaEntityToCategoryEntity - Debe retornar null cuando JPA es null")
    void jpa_to_category_entity_when_null() {
        CategoryEntity entity = mapper.fromCategoryJpaEntityToCategoryEntity(null);

        assertNull(entity);
    }

    @Test
    @DisplayName("fromCategoryEntityToCategoryJpaEntity - Debe retornar null cuando Entity es null")
    void category_entity_to_jpa_when_null() {
        CategoryJpaEntity jpa = mapper.fromCategoryEntityToCategoryJpaEntity(null);

        assertNull(jpa);
    }

    @Test
    @DisplayName("Mapper - Debe ser singleton")
    void mapper_is_singleton() {
        CategoryMapperPersistence instance1 = CategoryMapperPersistence.getInstance();
        CategoryMapperPersistence instance2 = CategoryMapperPersistence.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    @DisplayName("Mapeo bidireccional - Debe mantener consistencia de datos")
    void bidirectional_mapping_consistency() {
        CategoryJpaEntity originalJpa = new CategoryJpaEntity(1L, "Electrónica", "Dispositivos electrónicos");

        CategoryEntity entity = mapper.fromCategoryJpaEntityToCategoryEntity(originalJpa);
        CategoryJpaEntity mappedBackJpa = mapper.fromCategoryEntityToCategoryJpaEntity(entity);

        assertEquals(originalJpa.getId(), mappedBackJpa.getId());
        assertEquals(originalJpa.getName(), mappedBackJpa.getName());
        assertEquals(originalJpa.getDescription(), mappedBackJpa.getDescription());
    }

    @Test
    @DisplayName("fromCategoryJpaEntityToCategoryEntity - Debe mapear categoría sin ID")
    void jpa_to_category_entity_without_id() {
        CategoryJpaEntity jpa = new CategoryJpaEntity(null, "Deportes", "Artículos deportivos");

        CategoryEntity entity = mapper.fromCategoryJpaEntityToCategoryEntity(jpa);

        assertNotNull(entity);
        assertNull(entity.id());
        assertEquals("Deportes", entity.name());
        assertEquals("Artículos deportivos", entity.description());
    }

    @Test
    @DisplayName("fromCategoryEntityToCategoryJpaEntity - Debe mapear categoría sin ID")
    void category_entity_to_jpa_without_id() {
        CategoryEntity entity = new CategoryEntity(null, "Deportes", "Artículos deportivos");

        CategoryJpaEntity jpa = mapper.fromCategoryEntityToCategoryJpaEntity(entity);

        assertNotNull(jpa);
        assertNull(jpa.getId());
        assertEquals("Deportes", jpa.getName());
        assertEquals("Artículos deportivos", jpa.getDescription());
    }
}
