package org.example.storeback.domain.service.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CategoryDto Validation Tests")
class CategoryDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void shouldCreateValidCategoryDto_WithAllFields() {
        CategoryDto dto = new CategoryDto(1L, "Comida", "Productos alimenticios para mascotas");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
        assertEquals(1L, dto.id());
        assertEquals("Comida", dto.name());
        assertEquals("Productos alimenticios para mascotas", dto.description());
    }

    @Test
    void shouldCreateValidCategoryDto_WithNullId() {
        CategoryDto dto = new CategoryDto(null, "Nueva Categoría", "Descripción");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
        assertNull(dto.id());
        assertEquals("Nueva Categoría", dto.name());
    }

    @Test
    void shouldCreateValidCategoryDto_WithNullDescription() {
        CategoryDto dto = new CategoryDto(1L, "Sin Descripción", null);

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
        assertNull(dto.description());
    }

    @Test
    void shouldCreateValidCategoryDto_WithEmptyDescription() {
        CategoryDto dto = new CategoryDto(1L, "Nombre", "");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
        assertEquals("", dto.description());
    }


    @Test
    void validationFails_WhenNameIsNull() {
        CategoryDto dto = new CategoryDto(1L, null, "Descripción válida");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Debería haber violaciones");
        assertEquals(1, violations.size());

        ConstraintViolation<CategoryDto> violation = violations.iterator().next();
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("El nombre de la categoría no puede estar vacío", violation.getMessage());
    }

    @Test
    void validationFails_WhenNameIsEmpty() {
        CategoryDto dto = new CategoryDto(1L, "", "Descripción válida");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<CategoryDto> violation = violations.iterator().next();
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("El nombre de la categoría no puede estar vacío", violation.getMessage());
    }

    @Test
    void validationFails_WhenNameIsOnlyWhitespace() {
        CategoryDto dto = new CategoryDto(1L, "     ", "Descripción válida");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<CategoryDto> violation = violations.iterator().next();
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("El nombre de la categoría no puede estar vacío", violation.getMessage());
    }

    @Test
    void validationFails_WhenNameIsOnlyTabs() {
        CategoryDto dto = new CategoryDto(1L, "\t\t\t", "Descripción válida");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void validationFails_WhenNameIsOnlyNewLines() {
        CategoryDto dto = new CategoryDto(1L, "\n\n", "Descripción válida");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }


    @Test
    void validationFails_WhenNameExceeds100Characters() {
        String name = "A".repeat(101);
        CategoryDto dto = new CategoryDto(1L, name, "Descripción");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
            }

    @Test
    void validationPasses_WhenNameIs1Character() {
        CategoryDto dto = new CategoryDto(1L, "A", "Descripción");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void validationPasses_WhenNameIs50Characters() {
        String name = "A".repeat(50);
        CategoryDto dto = new CategoryDto(1L, name, "Descripción");

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }


    @Test
    void validationFails_WhenDescriptionExceeds500Characters() {
        String description = "B".repeat(501);
        CategoryDto dto = new CategoryDto(1L, "Nombre válido", description);

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<CategoryDto> violation = violations.iterator().next();
        assertEquals("description", violation.getPropertyPath().toString());
        assertEquals("La descripción no puede exceder 500 caracteres", violation.getMessage());
    }


    @Test
    void validationFails_WithMultipleViolations() {
        String longName = "A".repeat(101);
        String longDescription = "B".repeat(501);
        CategoryDto dto = new CategoryDto(1L, longName, longDescription);

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size(), "Deberían haber 2 violaciones");

    }

}
