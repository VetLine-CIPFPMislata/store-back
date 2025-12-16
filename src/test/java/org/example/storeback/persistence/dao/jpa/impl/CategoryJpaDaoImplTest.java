package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.storeback.config.TestConfig;
import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.persistence.dao.CategoryJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@DisplayName("CategoryJpaDaoImpl Tests")
class CategoryJpaDaoImplTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private CategoryJpaDao categoryJpaDao;

    @BeforeEach
    void setUp() {
        entityManager.createQuery("DELETE FROM CategoryJpaEntity").executeUpdate();
        entityManager.flush();
        entityManager.clear();
    }


    @Test
    @DisplayName("findAll - Debe retornar todas las categorías")
    void findAll_ShouldReturnAllCategories() {
        // Given
        CategoryJpaEntity category1 = new CategoryJpaEntity(null, "Comida", "Productos alimenticios");
        CategoryJpaEntity category2 = new CategoryJpaEntity(null, "Juguetes", "Juguetes para mascotas");
        entityManager.persist(category1);
        entityManager.persist(category2);
        entityManager.flush();


        List<CategoryEntity> result = categoryJpaDao.findAll();


        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("findAll - Debe retornar lista vacía cuando no hay categorías")
    void findAll_ShouldReturnEmptyList_WhenNoCategories() {
        // When
        List<CategoryEntity> result = categoryJpaDao.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    @DisplayName("findById - Debe retornar categoría cuando existe")
    void findById_ShouldReturnCategory_WhenExists() {
        // Given
        CategoryJpaEntity category = new CategoryJpaEntity(null, "Comida", "Productos alimenticios");
        entityManager.persist(category);
        entityManager.flush();
        Long categoryId = category.getId();

        // When
        Optional<CategoryEntity> result = categoryJpaDao.findById(categoryId);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Comida", result.get().name());
        assertEquals("Productos alimenticios", result.get().description());
    }

    @Test
    @DisplayName("findByName - Debe retornar categoría cuando existe por nombre")
    void findByName_ShouldReturnCategory_WhenExists() {

        CategoryJpaEntity category = new CategoryJpaEntity(null, "Comida", "Productos alimenticios");
        entityManager.persist(category);
        entityManager.flush();


        Optional<CategoryEntity> result = categoryJpaDao.findByName("Comida");


        assertTrue(result.isPresent());
        assertEquals("Comida", result.get().name());
    }

    @Test
    @DisplayName("save - Debe persistir nueva categoría con id null")
    void save_ShouldPersistNewCategory_WhenIdIsNull() {
        // Given
        CategoryEntity newCategory = new CategoryEntity(null, "Nueva", "Descripción");

        // When
        CategoryEntity result = categoryJpaDao.save(newCategory);

        // Then
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("Nueva", result.name());
        assertEquals("Descripción", result.description());

        // Verificar que se guardó en la base de datos
        CategoryJpaEntity saved = entityManager.find(CategoryJpaEntity.class, result.id());
        assertNotNull(saved);
        assertEquals("Nueva", saved.getName());
    }

    @Test
    @DisplayName("save - Debe actualizar categoría existente")
    void save_ShouldUpdateCategory_WhenIdIsNotNull() {
        // Given
        CategoryJpaEntity existing = new CategoryJpaEntity(null, "Comida", "Descripción original");
        entityManager.persist(existing);
        entityManager.flush();
        Long categoryId = existing.getId();

        CategoryEntity toUpdate = new CategoryEntity(categoryId, "Comida Actualizada", "Nueva descripción");


        CategoryEntity result = categoryJpaDao.save(toUpdate);

        assertNotNull(result);
        assertEquals(categoryId, result.id());
        assertEquals("Comida Actualizada", result.name());
        assertEquals("Nueva descripción", result.description());

        entityManager.clear();
        CategoryJpaEntity updated = entityManager.find(CategoryJpaEntity.class, categoryId);
        assertEquals("Comida Actualizada", updated.getName());
        assertEquals("Nueva descripción", updated.getDescription());
    }

    @Test
    @DisplayName("deleteById - Debe eliminar categoría")
    void deleteById_ShouldDeleteCategory() {

        CategoryJpaEntity category = new CategoryJpaEntity(null, "Comida", "Productos alimenticios");
        entityManager.persist(category);
        entityManager.flush();
        Long categoryId = category.getId();

        categoryJpaDao.deleteById(categoryId);
        entityManager.flush();

        CategoryJpaEntity deleted = entityManager.find(CategoryJpaEntity.class, categoryId);
        assertNull(deleted);
    }

}
