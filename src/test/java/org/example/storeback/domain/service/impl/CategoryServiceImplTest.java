package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.models.Category;
import org.example.storeback.domain.repository.CategoryRepository;
import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.domain.service.dto.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryServiceImpl Tests")
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryEntity categoryEntity1;
    private CategoryEntity categoryEntity2;
    private CategoryDto categoryDto;
    private CategoryDto categoryDtoWithoutId;

    @BeforeEach
    void setUp() {
        categoryEntity1 = new CategoryEntity(1L, "Comida", "Productos piensos");
        categoryEntity2 = new CategoryEntity(2L, "Juguetes", "Productos para el hogar");

        categoryDto = new CategoryDto(1L, "Comida", "Productos piensos");
        categoryDtoWithoutId = new CategoryDto(null, "Nueva Categoría", "Descripción nueva");
    }



    @Test
    @DisplayName("findAll - Debe retornar lista de categorías cuando existen categorías")
    void findAll_ShouldReturnListOfCategories_WhenCategoriesExist() {
        List<CategoryEntity> categoryEntities = Arrays.asList(categoryEntity1, categoryEntity2);
        when(categoryRepository.findAll()).thenReturn(categoryEntities);

        List<CategoryDto> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Comida", result.get(0).name());
        assertEquals("Juguetes", result.get(1).name());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - Debe retornar lista vacía cuando no hay categorías")
    void findAll_ShouldReturnEmptyList_WhenNoCategoriesExist() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<CategoryDto> result = categoryService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(categoryRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("findById - Debe retornar categoría cuando existe")
    void findById_ShouldReturnCategory_WhenCategoryExists() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity1));

        Optional<CategoryDto> result = categoryService.findById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(categoryId, result.get().id());
        assertEquals("Comida", result.get().name());
        assertEquals("Productos piensos", result.get().description());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("findById - Debe retornar Optional vacío cuando la categoría no existe")
    void findById_ShouldReturnEmptyOptional_WhenCategoryDoesNotExist() {

        Long categoryId = 999L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Optional<CategoryDto> result = categoryService.findById(categoryId);

        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("findById - Debe manejar null como parámetro")
    void findById_ShouldHandleNullParameter() {
        when(categoryRepository.findById(null)).thenReturn(Optional.empty());

        Optional<CategoryDto> result = categoryService.findById(null);

        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(null);
    }


    @Test
    @DisplayName("findByName - Debe retornar categoría cuando existe por nombre")
    void findByName_ShouldReturnCategory_WhenCategoryExistsByName() {
        String categoryName = "Comida";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(categoryEntity1));

        Optional<CategoryDto> result = categoryService.findByName(categoryName);

        assertTrue(result.isPresent());
        assertEquals(categoryName, result.get().name());
        assertEquals(1L, result.get().id());
        verify(categoryRepository).findByName(categoryName);
    }

    @Test
    @DisplayName("findByName - Debe retornar Optional vacío cuando no existe categoría con ese nombre")
    void findByName_ShouldReturnEmptyOptional_WhenCategoryDoesNotExistByName() {
        String categoryName = "Inexistente";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        Optional<CategoryDto> result = categoryService.findByName(categoryName);

        assertFalse(result.isPresent());
        verify(categoryRepository).findByName(categoryName);
    }

    @Test
    @DisplayName("findByName - Debe manejar búsqueda con nombre null")
    void findByName_ShouldHandleNullName() {
        when(categoryRepository.findByName(null)).thenReturn(Optional.empty());


        Optional<CategoryDto> result = categoryService.findByName(null);


        assertFalse(result.isPresent());
        verify(categoryRepository).findByName(null);
    }


    @Test
    @DisplayName("save - Debe crear nueva categoría cuando id es null")
    void save_ShouldCreateNewCategory_WhenIdIsNull() {
        CategoryEntity savedEntity = new CategoryEntity(3L, "Nueva Categoría", "Descripción nueva");
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(savedEntity);

        CategoryDto result = categoryService.save(categoryDtoWithoutId);


        assertNotNull(result);
        assertEquals(3L, result.id());
        assertEquals("Nueva Categoría", result.name());
        assertEquals("Descripción nueva", result.description());
        verify(categoryRepository).save(any(CategoryEntity.class));
        verify(categoryRepository, never()).findById(any());
    }

    @Test
    @DisplayName("save - Debe lanzar excepción cuando categoryDto es null")
    void save_ShouldThrowException_WhenCategoryDtoIsNull() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> categoryService.save(null)
        );

        assertEquals("CategoryDto cannot be null", exception.getMessage());
        verify(categoryRepository, never()).save(any());
    }


    @Test
    @DisplayName("save - Debe actualizar categoría existente cuando id no es null")
    void save_ShouldUpdateCategory_WhenIdIsNotNullAndCategoryExists() {
        Long categoryId = 1L;
        CategoryDto updateDto = new CategoryDto(categoryId, "Comida Actualizada", "Nueva descripción");
        CategoryEntity updatedEntity = new CategoryEntity(categoryId, "Comida Actualizada", "Nueva descripción");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity1));
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(updatedEntity);

        CategoryDto result = categoryService.save(updateDto);


        assertNotNull(result);
        assertEquals(categoryId, result.id());
        assertEquals("Comida Actualizada", result.name());
        assertEquals("Nueva descripción", result.description());
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).save(any(CategoryEntity.class));
    }

    @Test
    @DisplayName("save - Debe lanzar excepción cuando id existe pero categoría no se encuentra")
    void save_ShouldThrowException_WhenIdExistsButCategoryNotFound() {
        Long categoryId = 999L;
        CategoryDto updateDto = new CategoryDto(categoryId, "Categoría Inexistente", "Descripción");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> categoryService.save(updateDto)
        );

        assertEquals("Category with id " + categoryId + " not found", exception.getMessage());
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository, never()).save(any());
    }


    @Test
    @DisplayName("deleteById - Debe eliminar categoría cuando existe")
    void deleteById_ShouldDeleteCategory_WhenCategoryExists() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity1));
        doNothing().when(categoryRepository).deleteById(categoryId);

        categoryService.deleteById(categoryId);

        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    @DisplayName("deleteById - Debe lanzar excepción cuando la categoría no existe")
    void deleteById_ShouldThrowException_WhenCategoryDoesNotExist() {

        Long categoryId = 999L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> categoryService.deleteById(categoryId)
        );

        assertEquals("Category with id " + categoryId + " not found", exception.getMessage());
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("deleteById - Debe manejar correctamente la eliminación con verificación previa")
    void deleteById_ShouldHandleDeletionWithPriorVerification() {
        Long categoryId = 2L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity2));
        doNothing().when(categoryRepository).deleteById(categoryId);

        assertDoesNotThrow(() -> categoryService.deleteById(categoryId));

        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).deleteById(categoryId);
    }

}

