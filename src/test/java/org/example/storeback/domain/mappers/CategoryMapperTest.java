package org.example.storeback.domain.mappers;

import org.example.storeback.domain.models.Category;
import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.domain.service.dto.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CategoryMapper Tests")
class CategoryMapperTest {

    private CategoryMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = CategoryMapper.getInstance();
    }


    @Test
    @DisplayName("Debe retornar siempre la misma instancia")
    void getInstance_ShouldReturnSameInstance() {
        CategoryMapper instance1 = CategoryMapper.getInstance();
        CategoryMapper instance2 = CategoryMapper.getInstance();
        CategoryMapper instance3 = CategoryMapper.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertNotNull(instance3);
        assertSame(instance1, instance2);
        assertSame(instance2, instance3);
        assertSame(instance1, instance3);
    }


    @Test
    void fromCategoryEntityToCategory_ShouldMapCorrectly_WithAllFields() {
        CategoryEntity entity = new CategoryEntity(1L, "Comida", "Productos alimenticios para mascotas");

        Category result = mapper.fromCategoryEntityToCategory(entity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Comida", result.getName());
        assertEquals("Productos alimenticios para mascotas", result.getDescription());
    }

    @Test
    void fromCategoryEntityToCategory_ShouldThrowException_WhenEntityIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> mapper.fromCategoryEntityToCategory(null)
        );

        assertEquals("CategoryEntity cannot be null", exception.getMessage());
    }

    @Test
    void fromCategoryEntityToCategory_ShouldMap_WithNullId() {
        CategoryEntity entity = new CategoryEntity(null, "Nueva Categoría", "Sin ID");

        Category result = mapper.fromCategoryEntityToCategory(entity);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Nueva Categoría", result.getName());
        assertEquals("Sin ID", result.getDescription());
    }

    @Test
    void fromCategoryEntityToCategory_ShouldMap_WithNullDescription() {
        CategoryEntity entity = new CategoryEntity(5L, "Sin Descripción", null);

        Category result = mapper.fromCategoryEntityToCategory(entity);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        assertEquals("Sin Descripción", result.getName());
        assertNull(result.getDescription());
    }



    @Test
    void fromCategoryToCategoryEntity_ShouldMapCorrectly_WithAllFields() {
        Category category = new Category(2L, "Juguetes", "Juguetes para mascotas");

        CategoryEntity result = mapper.fromCategoryToCategoryEntity(category);

        assertNotNull(result);
        assertEquals(2L, result.id());
        assertEquals("Juguetes", result.name());
        assertEquals("Juguetes para mascotas", result.description());
    }

    @Test
    void fromCategoryToCategoryEntity_ShouldThrowException_WhenCategoryIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> mapper.fromCategoryToCategoryEntity(null)
        );

        assertEquals("Category cannot be null", exception.getMessage());
    }

    @Test
    void fromCategoryToCategoryEntity_ShouldMap_WithAllNullFields() {
        Category category = new Category(null, null, null);

        CategoryEntity result = mapper.fromCategoryToCategoryEntity(category);

        assertNotNull(result);
        assertNull(result.id());
        assertNull(result.name());
        assertNull(result.description());
    }


    @Test
    void fromCategoryDtoToCategory_ShouldMapCorrectly_WithAllFields() {
        CategoryDto dto = new CategoryDto(3L, "Accesorios", "Accesorios para mascotas");

        Category result = mapper.fromCategoryDtoToCategory(dto);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Accesorios", result.getName());
        assertEquals("Accesorios para mascotas", result.getDescription());
    }

    @Test
    void fromCategoryDtoToCategory_ShouldThrowException_WhenDtoIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> mapper.fromCategoryDtoToCategory(null)
        );

        assertEquals("CategoryDto cannot be null", exception.getMessage());
    }

    @Test
    void fromCategoryDtoToCategory_ShouldMap_WithNullId() {
        CategoryDto dto = new CategoryDto(null, "Nueva", "Sin ID aún");

        Category result = mapper.fromCategoryDtoToCategory(dto);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Nueva", result.getName());
    }


    @Test
    void fromCategoryToCategoryDto_ShouldMapCorrectly_WithAllFields() {
        Category category = new Category(4L, "Medicamentos", "Medicamentos veterinarios");

        CategoryDto result = mapper.fromCategoryToCategoryDto(category);

        assertNotNull(result);
        assertEquals(4L, result.id());
        assertEquals("Medicamentos", result.name());
        assertEquals("Medicamentos veterinarios", result.description());
    }

    @Test
    void fromCategoryToCategoryDto_ShouldThrowException_WhenCategoryIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> mapper.fromCategoryToCategoryDto(null)
        );

        assertEquals("Category cannot be null", exception.getMessage());
    }


    @Test
    @DisplayName("Mapear múltiples veces la misma instancia")
    void extremeCase_ShouldMapSameInstanceMultipleTimes() {
        CategoryEntity entity = new CategoryEntity(1L, "Test", "Description");

        Category result1 = mapper.fromCategoryEntityToCategory(entity);
        Category result2 = mapper.fromCategoryEntityToCategory(entity);
        Category result3 = mapper.fromCategoryEntityToCategory(entity);

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);

        assertNotSame(result1, result2);
        assertNotSame(result2, result3);

        assertEquals(result1.getId(), result2.getId());
        assertEquals(result2.getId(), result3.getId());
    }

}

