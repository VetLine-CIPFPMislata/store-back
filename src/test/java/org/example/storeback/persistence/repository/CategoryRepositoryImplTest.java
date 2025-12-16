package org.example.storeback.persistence.repository;

import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.persistence.dao.CategoryJpaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryRepositoryImpl Tests")
class CategoryRepositoryImplTest {

    @Mock
    private CategoryJpaDao categoryJpaDao;

    private CategoryRepositoryImpl categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = new CategoryRepositoryImpl(categoryJpaDao);
    }

    @Test
    @DisplayName("findAll - Debe delegar al DAO y retornar todas las categorías")
    void findAll_ShouldDelegateToDao() {
        CategoryEntity category1 = new CategoryEntity(1L, "Electrónica", "Dispositivos electrónicos");
        CategoryEntity category2 = new CategoryEntity(2L, "Ropa", "Prendas de vestir");
        List<CategoryEntity> expected = Arrays.asList(category1, category2);
        when(categoryJpaDao.findAll()).thenReturn(expected);

        List<CategoryEntity> result = categoryRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expected, result);
        verify(categoryJpaDao).findAll();
    }

    @Test
    @DisplayName("findById - Debe delegar al DAO y retornar categoría cuando existe")
    void findById_ShouldDelegateToDao_WhenCategoryExists() {
        Long categoryId = 1L;
        CategoryEntity expected = new CategoryEntity(categoryId, "Electrónica", "Dispositivos electrónicos");
        when(categoryJpaDao.findById(categoryId)).thenReturn(Optional.of(expected));

        Optional<CategoryEntity> result = categoryRepository.findById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(categoryJpaDao, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("findById - Debe retornar Optional vacío cuando no existe")
    void findById_ShouldReturnEmpty_WhenCategoryDoesNotExist() {
        Long categoryId = 999L;
        when(categoryJpaDao.findById(categoryId)).thenReturn(Optional.empty());

        Optional<CategoryEntity> result = categoryRepository.findById(categoryId);

        assertFalse(result.isPresent());
        verify(categoryJpaDao).findById(categoryId);
    }

    @Test
    @DisplayName("findByName - Debe delegar al DAO y retornar categoría por nombre")
    void findByName_ShouldDelegateToDao_WhenCategoryExists() {
        String categoryName = "Electrónica";
        CategoryEntity expected = new CategoryEntity(1L, categoryName, "Dispositivos electrónicos");
        when(categoryJpaDao.findByName(categoryName)).thenReturn(Optional.of(expected));

        Optional<CategoryEntity> result = categoryRepository.findByName(categoryName);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        assertEquals(categoryName, result.get().name());
        verify(categoryJpaDao, times(1)).findByName(categoryName);
    }


    @Test
    @DisplayName("save - Debe delegar al DAO para guardar nueva categoría")
    void save_ShouldDelegateToDao_WhenSavingNewCategory() {
        CategoryEntity newCategory = new CategoryEntity(null, "Deportes", "Artículos deportivos");
        CategoryEntity savedCategory = new CategoryEntity(1L, "Deportes", "Artículos deportivos");
        when(categoryJpaDao.save(newCategory)).thenReturn(savedCategory);

        CategoryEntity result = categoryRepository.save(newCategory);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("Deportes", result.name());
        verify(categoryJpaDao).save(newCategory);
    }

    @Test
    @DisplayName("save - Debe delegar al DAO para actualizar categoría existente")
    void save_ShouldDelegateToDao_WhenUpdatingCategory() {
        CategoryEntity existingCategory = new CategoryEntity(1L, "Electrónica Actualizada", "Nueva descripción");
        when(categoryJpaDao.save(existingCategory)).thenReturn(existingCategory);

        CategoryEntity result = categoryRepository.save(existingCategory);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Electrónica Actualizada", result.name());
        verify(categoryJpaDao).save(existingCategory);
    }

    @Test
    @DisplayName("deleteById - Debe delegar al DAO para eliminar categoría")
    void deleteById_ShouldDelegateToDao() {
        Long categoryId = 1L;
        doNothing().when(categoryJpaDao).deleteById(categoryId);


        categoryRepository.deleteById(categoryId);

        verify(categoryJpaDao).deleteById(categoryId);
    }

}

