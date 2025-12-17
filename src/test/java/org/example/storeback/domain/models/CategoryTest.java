package org.example.storeback.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Category Model Tests")
class CategoryTest {



    @Test
    void constructor_ShouldCreateCategory_WithAllFields() {
        Category category = new Category(1L, "Comida", "Productos alimenticios");

        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertEquals("Comida", category.getName());
        assertEquals("Productos alimenticios", category.getDescription());
    }

    @Test
    void constructor_ShouldCreateCategory_WithNullId() {
        Category category = new Category(null, "Nueva Categoría", "Sin ID");

        assertNotNull(category);
        assertNull(category.getId());
        assertEquals("Nueva Categoría", category.getName());
        assertEquals("Sin ID", category.getDescription());
    }



    @Test
    void constructor_ShouldNotCreateCategory_WithEmptyName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Category(1L, "", "Descripción válida")
        );

        assertEquals("El nombre de la categoría no puede estar vacío", exception.getMessage());
    }

    @Test
    void constructor_ShouldNotCreateCategory_WithEmptyDescription() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Category(1L, "Nombre válido", "")
        );

        assertEquals("La descripción de la categoría no puede estar vacía", exception.getMessage());
    }

    @Test
    void getId_ShouldReturnCorrectId() {
        Category category = new Category(42L, "Test", "Test");

        assertEquals(42L, category.getId());
    }

    @Test
    void getId_ShouldReturnNull_WhenIdIsNull() {
        Category category = new Category(null, "Test", "Test");

        assertNull(category.getId());
    }

    @Test
    void getName_ShouldReturnCorrectName() {
        Category category = new Category(1L, "Juguetes", "Test");

        assertEquals("Juguetes", category.getName());
    }


    @Test
    void getDescription_ShouldReturnCorrectDescription() {
        Category category = new Category(1L, "Test", "Descripción de prueba");

        assertEquals("Descripción de prueba", category.getDescription());
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
    void constructor_ShouldCreateCategory_WithDifferentNameFormats(String name) {
        Category category = new Category(1L, name, "Test");

        assertNotNull(category);
        assertEquals(name, category.getName());
    }


    @Test
    void constructor_ShouldNotCreateCategory_WithOnlyWhitespacesInName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Category(1L, "     ", "Descripción válida")
        );

        assertEquals("El nombre de la categoría no puede contener solo espacios en blanco", exception.getMessage());
    }

    @Test
    void constructor_ShouldNotCreateCategory_WithTabsInName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Category(1L, "Nombre\tCon\tTabs", "Descripción válida")
        );

        assertEquals("El nombre de la categoría no puede contener tabulaciones", exception.getMessage());
    }

    @Test
    void constructor_ShouldNotCreateCategory_WithTabsInDescription() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Category(1L, "Nombre válido", "Descripción\tCon\tTabs")
        );

        assertEquals("La descripción de la categoría no puede contener tabulaciones", exception.getMessage());
    }

}

