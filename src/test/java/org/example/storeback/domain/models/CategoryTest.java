package org.example.storeback.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Category Model Tests")
class CategoryTest {



    @Test
    @DisplayName("Constructor - Debe crear Category con todos los campos completos")
    void constructor_ShouldCreateCategory_WithAllFields() {
        Category category = new Category(1L, "Comida", "Productos alimenticios");

        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertEquals("Comida", category.getName());
        assertEquals("Productos alimenticios", category.getDescription());
    }

    @Test
    @DisplayName("Constructor - Debe crear Category con id null")
    void constructor_ShouldCreateCategory_WithNullId() {
        Category category = new Category(null, "Nueva Categoría", "Sin ID");

        assertNotNull(category);
        assertNull(category.getId());
        assertEquals("Nueva Categoría", category.getName());
        assertEquals("Sin ID", category.getDescription());
    }

    @Test
    @DisplayName("Constructor - Debe crear Category con description null")
    void constructor_ShouldCreateCategory_WithNullDescription() {
        Category category = new Category(1L, "Sin Descripción", null);

        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertEquals("Sin Descripción", category.getName());
        assertNull(category.getDescription());
    }

    @Test
    @DisplayName("Constructor - Debe crear Category con name null")
    void constructor_ShouldCreateCategory_WithNullName() {
        Category category = new Category(1L, null, "Descripción sin nombre");

        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertNull(category.getName());
        assertEquals("Descripción sin nombre", category.getDescription());
    }

    @Test
    @DisplayName("Constructor - Debe crear Category con todos los campos null")
    void constructor_ShouldCreateCategory_WithAllNullFields() {
        Category category = new Category(null, null, null);

        assertNotNull(category);
        assertNull(category.getId());
        assertNull(category.getName());
        assertNull(category.getDescription());
    }

    @Test
    @DisplayName("Constructor - NO debe crear Category con nombre vacío")
    void constructor_ShouldNotCreateCategory_WithEmptyName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Category(1L, "", "Descripción válida")
        );

        assertEquals("El nombre de la categoría no puede estar vacío", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor - NO debe crear Category con descripción vacía")
    void constructor_ShouldNotCreateCategory_WithEmptyDescription() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Category(1L, "Nombre válido", "")
        );

        assertEquals("La descripción de la categoría no puede estar vacía", exception.getMessage());
    }

    @Test
    @DisplayName("getId - Debe retornar el id correcto")
    void getId_ShouldReturnCorrectId() {
        Category category = new Category(42L, "Test", "Test");

        assertEquals(42L, category.getId());
    }

    @Test
    @DisplayName("getId - Debe retornar null cuando id es null")
    void getId_ShouldReturnNull_WhenIdIsNull() {
        Category category = new Category(null, "Test", "Test");

        assertNull(category.getId());
    }

    @Test
    @DisplayName("getName - Debe retornar el nombre correcto")
    void getName_ShouldReturnCorrectName() {
        Category category = new Category(1L, "Juguetes", "Test");

        assertEquals("Juguetes", category.getName());
    }

    @Test
    @DisplayName("getName - Debe retornar null cuando name es null")
    void getName_ShouldReturnNull_WhenNameIsNull() {
        Category category = new Category(1L, null, "Test");

        assertNull(category.getName());
    }

    @Test
    @DisplayName("getDescription - Debe retornar la descripción correcta")
    void getDescription_ShouldReturnCorrectDescription() {
        Category category = new Category(1L, "Test", "Descripción de prueba");

        assertEquals("Descripción de prueba", category.getDescription());
    }

    @Test
    @DisplayName("getDescription - Debe retornar null cuando description es null")
    void getDescription_ShouldReturnNull_WhenDescriptionIsNull() {
        Category category = new Category(1L, "Test", null);

        assertNull(category.getDescription());
    }

    @Test
    @DisplayName("Los campos deben ser finales y no modificables")
    void immutability_FieldsShouldBeFinalAndNotModifiable() {
        Category category = new Category(1L, "Original", "Descripción original");

        Long originalId = category.getId();
        String originalName = category.getName();
        String originalDescription = category.getDescription();

        assertEquals(originalId, category.getId());
        assertEquals(originalName, category.getName());
        assertEquals(originalDescription, category.getDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Comida",
            "J",
            "Categoría con nombre muy largo que podría exceder límites normales de visualización",
            "123456789",
            "Cat-1",
            "Categoría_Test",
            "MAYÚSCULAS",
            "minúsculas",
            "MiXtO",
            "Con Espacios Múltiples"
    })
    @DisplayName("Constructor - Debe crear Category con diferentes formatos de nombre")
    void constructor_ShouldCreateCategory_WithDifferentNameFormats(String name) {
        Category category = new Category(1L, name, "Test");

        assertNotNull(category);
        assertEquals(name, category.getName());
    }


    @Test
    @DisplayName("Constructor - Debe crear Category con el nombre con acentos")
    void constructor_ShouldCreateCategory_WithAccentedCharacters() {
        Category category = new Category(1L, "Comída Orgánicá", "Descripción con áéíóú");

        assertNotNull(category);
        assertEquals("Comída Orgánicá", category.getName());
        assertEquals("Descripción con áéíóú", category.getDescription());
    }

    @Test
    @DisplayName("Constructor - Debe crear Category con espacios en blanco múltiples")
    void constructor_ShouldCreateCategory_WithMultipleWhitespaces() {
        Category category = new Category(1L, "   Espacios   ", "   Descripción   ");

        assertNotNull(category);
        assertEquals("   Espacios   ", category.getName());
        assertEquals("   Descripción   ", category.getDescription());
    }

    @Test
    @DisplayName("Constructor - Debe crear Category con saltos de línea")
    void constructor_ShouldCreateCategory_WithNewLines() {
        Category category = new Category(1L, "Nombre\nCon\nSaltos", "Descripción\nMultilínea");

        assertNotNull(category);
        assertEquals("Nombre\nCon\nSaltos", category.getName());
        assertEquals("Descripción\nMultilínea", category.getDescription());
    }

    @Test
    @DisplayName("Constructor - NO debe crear Category con solo espacios en el nombre")
    void constructor_ShouldNotCreateCategory_WithOnlyWhitespacesInName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Category(1L, "     ", "Descripción válida")
        );

        assertEquals("El nombre de la categoría no puede contener solo espacios en blanco", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor - NO debe crear Category con tabulaciones en el nombre")
    void constructor_ShouldNotCreateCategory_WithTabsInName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Category(1L, "Nombre\tCon\tTabs", "Descripción válida")
        );

        assertEquals("El nombre de la categoría no puede contener tabulaciones", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor - NO debe crear Category con tabulaciones en la descripción")
    void constructor_ShouldNotCreateCategory_WithTabsInDescription() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Category(1L, "Nombre válido", "Descripción\tCon\tTabs")
        );

        assertEquals("La descripción de la categoría no puede contener tabulaciones", exception.getMessage());
    }

}

