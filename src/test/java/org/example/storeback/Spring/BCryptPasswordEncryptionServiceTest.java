package org.example.storeback.Spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BCryptPasswordEncryptionService Tests")
class BCryptPasswordEncryptionServiceTest {

    private BCryptPasswordEncryptionService encryptionService;

    @BeforeEach
    void setUp() {
        encryptionService = new BCryptPasswordEncryptionService();
    }

    @Test
    @DisplayName("Debe encriptar una contraseña correctamente")
    void encryptPassword_ShouldEncryptSuccessfully() {
        // Given
        String rawPassword = "mySecurePassword123";

        // When
        String encryptedPassword = encryptionService.encryptPassword(rawPassword);

        // Then
        assertNotNull(encryptedPassword, "La contraseña encriptada no debe ser null");
        assertNotEquals(rawPassword, encryptedPassword, "La contraseña encriptada debe ser diferente de la original");
        assertTrue(encryptedPassword.startsWith("$2a$"), "Debe usar el formato BCrypt");
    }


    @Test
    @DisplayName("Debe validar correctamente una contraseña que coincide")
    void matches_WithMatchingPassword_ShouldReturnTrue() {
        // Given
        String rawPassword = "mySecurePassword123";
        String encryptedPassword = encryptionService.encryptPassword(rawPassword);

        // When
        boolean matches = encryptionService.matches(rawPassword, encryptedPassword);

        // Then
        assertTrue(matches, "La contraseña debe coincidir con su versión encriptada");
    }

    @Test
    @DisplayName("Debe validar incorrectamente una contraseña que no coincide")
    void matches_WithNonMatchingPassword_ShouldReturnFalse() {
        // Given
        String rawPassword = "mySecurePassword123";
        String wrongPassword = "wrongPassword";
        String encryptedPassword = encryptionService.encryptPassword(rawPassword);

        // When
        boolean matches = encryptionService.matches(wrongPassword, encryptedPassword);

        // Then
        assertFalse(matches, "La contraseña incorrecta no debe coincidir");
    }

    @Test
    @DisplayName("Debe retornar false cuando la contraseña encriptada es null")
    void matches_WithNullEncryptedPassword_ShouldReturnFalse() {
        // Given
        String rawPassword = "mySecurePassword123";

        // When
        boolean matches = encryptionService.matches(rawPassword, null);

        // Then
        assertFalse(matches, "Debe retornar false cuando la contraseña encriptada es null");
    }

    @Test
    @DisplayName("Debe retornar false cuando la contraseña raw es null")
    void matches_WithNullRawPassword_ShouldReturnFalse() {
        // Given
        String encryptedPassword = encryptionService.encryptPassword("somePassword");

        // When
        boolean matches = encryptionService.matches(null, encryptedPassword);

        // Then
        assertFalse(matches, "Debe retornar false cuando la contraseña raw es null");
    }

    @Test
    @DisplayName("Dos encriptaciones de la misma contraseña deben ser diferentes (salt aleatorio)")
    void encryptPassword_SamePasswordTwice_ShouldProduceDifferentHashes() {
        // Given
        String rawPassword = "mySecurePassword123";

        // When
        String encrypted1 = encryptionService.encryptPassword(rawPassword);
        String encrypted2 = encryptionService.encryptPassword(rawPassword);

        // Then
        assertNotEquals(encrypted1, encrypted2, "Dos hashes de la misma contraseña deben ser diferentes");
        assertTrue(encryptionService.matches(rawPassword, encrypted1), "Primera encriptación debe coincidir");
        assertTrue(encryptionService.matches(rawPassword, encrypted2), "Segunda encriptación debe coincidir");
    }

    @Test
    @DisplayName("Debe validar correctamente contraseñas con caracteres especiales")
    void encryptPassword_WithSpecialCharacters_ShouldWorkCorrectly() {
        // Given
        String complexPassword = "P@ssw0rd!#$%&*()_+-=[]{}|;:',.<>?/~`";

        // When
        String encryptedPassword = encryptionService.encryptPassword(complexPassword);
        boolean matches = encryptionService.matches(complexPassword, encryptedPassword);

        // Then
        assertNotNull(encryptedPassword);
        assertTrue(matches, "Debe manejar correctamente caracteres especiales");
    }

    @Test
    @DisplayName("Debe validar correctamente contraseñas largas")
    void encryptPassword_WithLongPassword_ShouldWorkCorrectly() {
        // Given
        String longPassword = "a".repeat(200); // Contraseña de 200 caracteres

        // When
        String encryptedPassword = encryptionService.encryptPassword(longPassword);
        boolean matches = encryptionService.matches(longPassword, encryptedPassword);

        // Then
        assertNotNull(encryptedPassword);
        assertTrue(matches, "Debe manejar correctamente contraseñas largas");
    }

    @Test
    @DisplayName("Debe ser case-sensitive al validar contraseñas")
    void matches_ShouldBeCaseSensitive() {
        // Given
        String password = "MyPassword123";
        String encryptedPassword = encryptionService.encryptPassword(password);

        // When
        boolean matchesLower = encryptionService.matches("mypassword123", encryptedPassword);
        boolean matchesUpper = encryptionService.matches("MYPASSWORD123", encryptedPassword);
        boolean matchesCorrect = encryptionService.matches(password, encryptedPassword);

        // Then
        assertFalse(matchesLower, "No debe coincidir con minúsculas");
        assertFalse(matchesUpper, "No debe coincidir con mayúsculas");
        assertTrue(matchesCorrect, "Debe coincidir con la contraseña correcta");
    }
}

