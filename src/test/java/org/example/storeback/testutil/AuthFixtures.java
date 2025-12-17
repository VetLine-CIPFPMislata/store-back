package org.example.storeback.testutil;

import org.example.storeback.controller.webmodel.request.LoginRequest;
import org.example.storeback.controller.webmodel.response.LoginResponse;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.domain.repository.entity.SessionEntity;
import org.example.storeback.domain.service.dto.ClientDto;

import java.time.LocalDateTime;

/**
 * Clase de utilidades para crear objetos de prueba relacionados con autenticación.
 */
public class AuthFixtures {

    public static final String ADMIN_TOKEN = "admin-token-123456";
    public static final String USER_TOKEN = "user-token-123456";
    public static final String INVALID_TOKEN = "invalid-token";

    public static final String ADMIN_EMAIL = "admin@example.com";
    public static final String USER_EMAIL = "user@example.com";
    public static final String INVALID_EMAIL = "invalid@example.com";

    public static final String VALID_PASSWORD = "password123";
    public static final String INVALID_PASSWORD = "wrongpassword";
    public static final String ENCRYPTED_PASSWORD = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy"; // BCrypt hash of "password123"

    /**
     * Crea un ClientDto de tipo ADMIN.
     */
    public static ClientDto createAdminClientDto() {
        return new ClientDto(
                1L,
                "Admin User",
                ADMIN_EMAIL,
                ENCRYPTED_PASSWORD,
                "123456789",
                null,
                Role.ADMIN
        );
    }

    /**
     * Crea un ClientDto de tipo USER.
     */
    public static ClientDto createUserClientDto() {
        return new ClientDto(
                2L,
                "Regular User",
                USER_EMAIL,
                ENCRYPTED_PASSWORD,
                "987654321",
                null,
                Role.USER
        );
    }

    /**
     * Crea un ClientDto personalizado.
     */
    public static ClientDto createClientDto(Long id, String name, String email, String password, Role role) {
        return new ClientDto(id, name, email, password, "123456789", null, role);
    }

    /**
     * Crea un ClientEntity de tipo ADMIN.
     */
    public static ClientEntity createAdminClientEntity() {
        return new ClientEntity(
                1L,
                "Admin User",
                ADMIN_EMAIL,
                ENCRYPTED_PASSWORD,
                "123456789",
                null,
                Role.ADMIN
        );
    }

    /**
     * Crea un ClientEntity de tipo USER.
     */
    public static ClientEntity createUserClientEntity() {
        return new ClientEntity(
                2L,
                "Regular User",
                USER_EMAIL,
                ENCRYPTED_PASSWORD,
                "987654321",
                null,
                Role.USER
        );
    }

    /**
     * Crea una SessionEntity para un admin.
     */
    public static SessionEntity createAdminSession() {
        return new SessionEntity(1L, 1L, ADMIN_TOKEN, LocalDateTime.now());
    }

    /**
     * Crea una SessionEntity para un usuario regular.
     */
    public static SessionEntity createUserSession() {
        return new SessionEntity(2L, 2L, USER_TOKEN, LocalDateTime.now());
    }

    /**
     * Crea una SessionEntity personalizada.
     */
    public static SessionEntity createSession(Long sessionId, String token, Long clientId) {
        return new SessionEntity(sessionId, clientId, token, LocalDateTime.now());
    }

    /**
     * Crea una SessionEntity expirada (1 hora atrás).
     */
    public static SessionEntity createExpiredSession() {
        return new SessionEntity(3L, 1L, "expired-token", LocalDateTime.now().minusHours(1));
    }

    /**
     * Crea un LoginRequest válido.
     */
    public static LoginRequest createValidLoginRequest() {
        return new LoginRequest(ADMIN_EMAIL, VALID_PASSWORD);
    }

    /**
     * Crea un LoginRequest con email inválido.
     */
    public static LoginRequest createInvalidEmailLoginRequest() {
        return new LoginRequest(INVALID_EMAIL, VALID_PASSWORD);
    }

    /**
     * Crea un LoginRequest con contraseña incorrecta.
     */
    public static LoginRequest createInvalidPasswordLoginRequest() {
        return new LoginRequest(ADMIN_EMAIL, INVALID_PASSWORD);
    }

    /**
     * Crea un LoginRequest personalizado.
     */
    public static LoginRequest createLoginRequest(String email, String password) {
        return new LoginRequest(email, password);
    }

    /**
     * Crea un LoginResponse.
     */
    public static LoginResponse createLoginResponse(String token, String email, String name, Role role) {
        return new LoginResponse(token, email, name, role);
    }

    /**
     * Crea un LoginResponse para admin.
     */
    public static LoginResponse createAdminLoginResponse() {
        return new LoginResponse(ADMIN_TOKEN, ADMIN_EMAIL, "Admin User", Role.ADMIN);
    }

    /**
     * Crea un LoginResponse para user.
     */
    public static LoginResponse createUserLoginResponse() {
        return new LoginResponse(USER_TOKEN, USER_EMAIL, "Regular User", Role.USER);
    }
}

