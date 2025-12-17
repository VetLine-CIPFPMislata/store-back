package org.example.storeback.controller;

import org.example.storeback.controller.webmodel.request.CategoryInsertRequest;
import org.example.storeback.controller.webmodel.request.CategoryUpdateRequest;
import org.example.storeback.controller.webmodel.response.CategoryResponse;
import org.example.storeback.domain.service.CategoryService;
import org.example.storeback.domain.service.dto.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryController Tests")
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryDto categoryDto1;
    private CategoryDto categoryDto2;

    @BeforeEach
    void setUp() {
        categoryDto1 = new CategoryDto(1L, "Comida", "Productos alimenticios");
        categoryDto2 = new CategoryDto(2L, "Juguetes", "Juguetes para mascotas");
    }


    @Test
    void findAllCategories_ShouldReturnAllCategories() {

        List<CategoryDto> categories = Arrays.asList(categoryDto1, categoryDto2);
        when(categoryService.findAll()).thenReturn(categories);

        ResponseEntity<List<CategoryResponse>> response = categoryController.findAllCategories();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Comida", response.getBody().get(0).name());
        assertEquals("Juguetes", response.getBody().get(1).name());
        verify(categoryService).findAll();
    }



    @Test
    void findCategoryById_ShouldReturnCategory_WhenExists() {

        Long categoryId = 1L;
        when(categoryService.findById(categoryId)).thenReturn(Optional.of(categoryDto1));


        ResponseEntity<CategoryResponse> response = categoryController.findCategoryById(categoryId);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
        assertEquals("Comida", response.getBody().name());
        verify(categoryService).findById(categoryId);
    }

    @Test
    void findCategoryById_ShouldReturn404_WhenNotExists() {
        Long categoryId = 999L;
        when(categoryService.findById(categoryId)).thenReturn(Optional.empty());


        ResponseEntity<CategoryResponse> response = categoryController.findCategoryById(categoryId);


        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(categoryService).findById(categoryId);
    }


    @Test
    void findCategoryByName_ShouldReturnCategory_WhenExists() {

        String categoryName = "Comida";
        when(categoryService.findByName(categoryName)).thenReturn(Optional.of(categoryDto1));


        ResponseEntity<CategoryResponse> response = categoryController.findCategoryByName(categoryName);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Comida", response.getBody().name());
        verify(categoryService).findByName(categoryName);
    }

    @Test
    void findCategoryByName_ShouldReturn404_WhenNotExists() {

        String categoryName = "NoExiste";
        when(categoryService.findByName(categoryName)).thenReturn(Optional.empty());


        ResponseEntity<CategoryResponse> response = categoryController.findCategoryByName(categoryName);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(categoryService).findByName(categoryName);
    }


    @Test
    void createCategory_ShouldCreateCategory_WithValidData() {

        CategoryInsertRequest request = new CategoryInsertRequest("Nueva Categoría", "Descripción válida");
        CategoryDto savedCategory = new CategoryDto(3L, "Nueva Categoría", "Descripción válida");
        when(categoryService.save(any(CategoryDto.class))).thenReturn(savedCategory);

        ResponseEntity<CategoryResponse> response = categoryController.createCategory(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3L, response.getBody().id());
        assertEquals("Nueva Categoría", response.getBody().name());
    }

    @Test
    void createCategory_ShouldReturn400_WithEmptyName() {
        CategoryInsertRequest request = new CategoryInsertRequest("", "Descripción válida");

        ResponseEntity<CategoryResponse> response = categoryController.createCategory(request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createCategory_ShouldReturn400_WhenNameExceedsLimit() {

        String longName = "A".repeat(101);
        CategoryInsertRequest request = new CategoryInsertRequest(longName, "Descripción");

        ResponseEntity<CategoryResponse> response = categoryController.createCategory(request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

    }


    @Test
    void updateCategory_ShouldUpdateCategory_WhenExists() {

        Long categoryId = 1L;
        CategoryUpdateRequest request = new CategoryUpdateRequest("Comida Actualizada", "Nueva descripción");
        CategoryDto updatedCategory = new CategoryDto(categoryId, "Comida Actualizada", "Nueva descripción");

        when(categoryService.findById(categoryId)).thenReturn(Optional.of(categoryDto1));
        when(categoryService.save(any(CategoryDto.class))).thenReturn(updatedCategory);


        ResponseEntity<CategoryResponse> response = categoryController.updateCategory(categoryId, request);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Comida Actualizada", response.getBody().name());
        verify(categoryService).findById(categoryId);

    }

    @Test
    void updateCategory_ShouldReturn404_WhenCategoryNotExists() {
        Long categoryId = 999L;
        CategoryUpdateRequest request = new CategoryUpdateRequest("Nombre", "Descripción");
        when(categoryService.findById(categoryId)).thenReturn(Optional.empty());


        ResponseEntity<CategoryResponse> response = categoryController.updateCategory(categoryId, request);


        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(categoryService).findById(categoryId);
    }

    @Test
    void updateCategory_ShouldReturn400_WithInvalidData() {
        Long categoryId = 1L;
        CategoryUpdateRequest request = new CategoryUpdateRequest("", "Descripción");

        ResponseEntity<CategoryResponse> response = categoryController.updateCategory(categoryId, request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

    }


    @Test
    @DisplayName("DELETE /api/categories/{id} - Debe eliminar categoría")
    void deleteCategory_ShouldDeleteCategory() {

        Long categoryId = 1L;
        doNothing().when(categoryService).deleteById(categoryId);

        ResponseEntity<Void> response = categoryController.deleteCategory(categoryId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(categoryService).deleteById(categoryId);
    }

    @Test
    void deleteCategory_ShouldHandleNonExistentCategory() {

        Long categoryId = 999L;
        doThrow(new IllegalArgumentException("Category with id " + categoryId + " not found"))
                .when(categoryService).deleteById(categoryId);

        assertThrows(IllegalArgumentException.class, () -> {
            categoryController.deleteCategory(categoryId);
        });

        verify(categoryService).deleteById(categoryId);
    }
}

