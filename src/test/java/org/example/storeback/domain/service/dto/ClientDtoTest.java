package org.example.storeback.domain.service.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.storeback.domain.models.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClientDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidClientDto() {
        ClientDto clientDto = new ClientDto(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "password123",
                "+34600111222",
                10L,
                Role.USER
        );

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(clientDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testClientDtoWithBlankName() {
        ClientDto clientDto = new ClientDto(
                1L,
                "",
                "juan@example.com",
                "password123",
                "+34600111222",
                10L,
                Role.USER
        );

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(clientDto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("nombre no puede estar vacío")));
    }

    @Test
    void testClientDtoWithInvalidEmail() {
        ClientDto clientDto = new ClientDto(
                1L,
                "Juan Pérez",
                "invalid-email",
                "password123",
                "+34600111222",
                10L,
                Role.USER
        );

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(clientDto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void testClientDtoWithBlankPassword() {
        ClientDto clientDto = new ClientDto(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "",
                "+34600111222",
                10L,
                Role.USER
        );

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(clientDto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void testClientDtoWithTooLongPhone() {
        ClientDto clientDto = new ClientDto(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "password123",
                "+34600111222333444555",
                10L,
                Role.USER
        );

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(clientDto);

        assertFalse(violations.isEmpty());
    }
    @Test
    void testClientDtoWithNullRole() {
        ClientDto clientDto = new ClientDto(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "password123",
                "+34600111222",
                10L,
                null
        );

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(clientDto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void testClientDtoWithAdminRole() {
        ClientDto clientDto = new ClientDto(
                1L,
                "Admin User",
                "admin@store.com",
                "admin123",
                "+34600222333",
                1L,
                Role.ADMIN
        );

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(clientDto);

        assertTrue(violations.isEmpty());
        assertEquals(Role.ADMIN, clientDto.role());
    }

    @Test
    void testClientDtoWithNullCartId() {
        ClientDto clientDto = new ClientDto(
                1L,
                "María García",
                "maria@example.com",
                "password123",
                "+34600444555",
                null,
                Role.USER
        );

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(clientDto);

        assertTrue(violations.isEmpty());
        assertNull(clientDto.cartId());
    }

    @Test
    void testClientDtoWithNullPhone() {
        ClientDto clientDto = new ClientDto(
                1L,
                "Test User",
                "test@example.com",
                "password123",
                null,
                10L,
                Role.USER
        );

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(clientDto);

        assertTrue(violations.isEmpty());
        assertNull(clientDto.phone());
    }

    @Test
    void testClientDtoRecordMethods() {
        ClientDto clientDto = new ClientDto(
                5L,
                "Test User",
                "test@example.com",
                "testpass",
                "+34600666777",
                15L,
                Role.USER
        );

        assertEquals(5L, clientDto.id());
        assertEquals("Test User", clientDto.name());
        assertEquals("test@example.com", clientDto.email());
        assertEquals("testpass", clientDto.password());
        assertEquals("+34600666777", clientDto.phone());
        assertEquals(15L, clientDto.cartId());
        assertEquals(Role.USER, clientDto.role());
    }
}
