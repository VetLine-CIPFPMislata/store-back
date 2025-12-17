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
    void findAll_ShouldReturnAllCategories() {
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
    void findAll_ShouldReturnEmptyList_WhenNoCategories() {
        List<CategoryEntity> result = categoryJpaDao.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    void findById_ShouldReturnCategory_WhenExists() {
        CategoryJpaEntity category = new CategoryJpaEntity(null, "Comida", "Productos alimenticios");
        entityManager.persist(category);
        entityManager.flush();
        Long categoryId = category.getId();

        Optional<CategoryEntity> result = categoryJpaDao.findById(categoryId);

        assertTrue(result.isPresent());
        assertEquals("Comida", result.get().name());
        assertEquals("Productos alimenticios", result.get().description());
    }

    @Test
    void findByName_ShouldReturnCategory_WhenExists() {

        CategoryJpaEntity category = new CategoryJpaEntity(null, "Comida", "Productos alimenticios");
        entityManager.persist(category);
        entityManager.flush();


        Optional<CategoryEntity> result = categoryJpaDao.findByName("Comida");


        assertTrue(result.isPresent());
        assertEquals("Comida", result.get().name());
    }

    @Test
    void save_ShouldPersistNewCategory_WhenIdIsNull() {
        CategoryEntity newCategory = new CategoryEntity(null, "Nueva", "Descripción");

        CategoryEntity result = categoryJpaDao.save(newCategory);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("Nueva", result.name());
        assertEquals("Descripción", result.description());

        CategoryJpaEntity saved = entityManager.find(CategoryJpaEntity.class, result.id());
        assertNotNull(saved);
        assertEquals("Nueva", saved.getName());
    }

    @Test
    void save_ShouldUpdateCategory_WhenIdIsNotNull() {

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
