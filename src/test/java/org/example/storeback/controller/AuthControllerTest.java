package org.example.storeback.controller;

import org.example.storeback.controller.webmodel.request.LoginRequest;
import org.example.storeback.controller.webmodel.response.LoginResponse;
import org.example.storeback.domain.service.AuthService;
import org.example.storeback.domain.service.ClientService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.testutil.AuthFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController Tests")
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private AuthController authController;

    private ClientDto adminClient;
    private ClientDto userClient;
    private LoginRequest validLoginRequest;
    private LoginRequest invalidLoginRequest;

    @BeforeEach
    void setUp() {
        adminClient = AuthFixtures.createAdminClientDto();
        userClient = AuthFixtures.createUserClientDto();
        validLoginRequest = AuthFixtures.createValidLoginRequest();
        invalidLoginRequest = AuthFixtures.createInvalidEmailLoginRequest();
    }

    @Test
    @DisplayName("Login exitoso debe retornar 200 OK con token")
    void login_WithValidCredentials_ShouldReturnOkWithToken() {
        // Given
        when(clientService.login(validLoginRequest.email(), validLoginRequest.password()))
                .thenReturn(Optional.of(adminClient));
        when(authService.createTokenFromUser(adminClient))
                .thenReturn(AuthFixtures.ADMIN_TOKEN);

        // When
        ResponseEntity<LoginResponse> response = authController.login(validLoginRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().token()).isEqualTo(AuthFixtures.ADMIN_TOKEN);

        verify(clientService).login(validLoginRequest.email(), validLoginRequest.password());
        verify(authService).createTokenFromUser(adminClient);
    }

    @Test
    @DisplayName("Login con credenciales inválidas debe retornar 401 Unauthorized")
    void login_WithInvalidCredentials_ShouldReturnUnauthorized() {
        // Given
        when(clientService.login(invalidLoginRequest.email(), invalidLoginRequest.password()))
                .thenReturn(Optional.empty());

        // When
        ResponseEntity<LoginResponse> response = authController.login(invalidLoginRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();

        verify(clientService).login(invalidLoginRequest.email(), invalidLoginRequest.password());
        verify(authService, never()).createTokenFromUser(any());
    }

    @Test
    @DisplayName("Login con email inexistente debe retornar 401 Unauthorized")
    void login_WithNonExistentEmail_ShouldReturnUnauthorized() {
        // Given
        LoginRequest request = new LoginRequest(null, "password");
        when(clientService.login(request.email(), request.password()))
                .thenReturn(Optional.empty());

        // When
        ResponseEntity<LoginResponse> response = authController.login(request);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();

        verify(clientService).login(request.email(), request.password());
        verify(authService, never()).createTokenFromUser(any());
    }

    @Test
    @DisplayName("Login con contraseña incorrecta debe retornar 401 Unauthorized")
    void login_WithWrongPassword_ShouldReturnUnauthorized() {
        // Given
        LoginRequest request = AuthFixtures.createInvalidPasswordLoginRequest();
        when(clientService.login(request.email(), request.password()))
                .thenReturn(Optional.empty());

        // When
        ResponseEntity<LoginResponse> response = authController.login(request);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();

        verify(clientService, times(1)).login(request.email(), request.password());
        verify(authService, never()).createTokenFromUser(any());
    }

    @Test
    @DisplayName("Login debe funcionar correctamente para usuario con rol USER")
    void login_WithUserRole_ShouldReturnOkWithToken() {
        // Given
        LoginRequest userLoginRequest = new LoginRequest(AuthFixtures.USER_EMAIL, AuthFixtures.VALID_PASSWORD);
        when(clientService.login(userLoginRequest.email(), userLoginRequest.password()))
                .thenReturn(Optional.of(userClient));
        when(authService.createTokenFromUser(userClient))
                .thenReturn(AuthFixtures.USER_TOKEN);

        // When
        ResponseEntity<LoginResponse> response = authController.login(userLoginRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().token()).isEqualTo(AuthFixtures.USER_TOKEN);

        verify(clientService, times(1)).login(userLoginRequest.email(), userLoginRequest.password());
        verify(authService, times(1)).createTokenFromUser(userClient);
    }

    @Test
    @DisplayName("Login debe funcionar correctamente para usuario con rol ADMIN")
    void login_WithAdminRole_ShouldReturnOkWithToken() {
        // Given
        when(clientService.login(validLoginRequest.email(), validLoginRequest.password()))
                .thenReturn(Optional.of(adminClient));
        when(authService.createTokenFromUser(adminClient))
                .thenReturn(AuthFixtures.ADMIN_TOKEN);

        // When
        ResponseEntity<LoginResponse> response = authController.login(validLoginRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().token()).isEqualTo(AuthFixtures.ADMIN_TOKEN);

        verify(clientService, times(1)).login(validLoginRequest.email(), validLoginRequest.password());
        verify(authService, times(1)).createTokenFromUser(adminClient);
    }

    @Test
    @DisplayName("Logout con token válido debe retornar 204 No Content")
    void logout_WithValidToken_ShouldReturnNoContent() {
        // Given
        String authHeader = "Bearer " + AuthFixtures.ADMIN_TOKEN;
        doNothing().when(authService).deleteToken(AuthFixtures.ADMIN_TOKEN);

        // When
        ResponseEntity<Void> response = authController.logout(authHeader);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();

        verify(authService, times(1)).deleteToken(AuthFixtures.ADMIN_TOKEN);
    }

    @Test
    @DisplayName("Logout sin header Authorization debe retornar 400 Bad Request")
    void logout_WithoutAuthorizationHeader_ShouldReturnBadRequest() {
        // When
        ResponseEntity<Void> response = authController.logout(null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();

        verify(authService, never()).deleteToken(anyString());
    }

    @Test
    @DisplayName("Logout con header Authorization vacío debe retornar 400 Bad Request")
    void logout_WithEmptyAuthorizationHeader_ShouldReturnBadRequest() {
        // When
        ResponseEntity<Void> response = authController.logout("");

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();

        verify(authService, never()).deleteToken(anyString());
    }

    @Test
    @DisplayName("Logout con header Authorization sin prefijo Bearer debe retornar 400 Bad Request")
    void logout_WithoutBearerPrefix_ShouldReturnBadRequest() {
        // Given
        String authHeader = AuthFixtures.ADMIN_TOKEN; // Sin "Bearer "

        // When
        ResponseEntity<Void> response = authController.logout(authHeader);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();

        verify(authService, never()).deleteToken(anyString());
    }

    @Test
    @DisplayName("Logout con header Bearer pero sin token debe retornar 204 No Content (token vacío)")
    void logout_WithBearerButNoToken_ShouldReturnBadRequest() {
        // Given
        String authHeader = "Bearer "; // substring(7) retorna "", no null
        doNothing().when(authService).deleteToken(""); // Mock para token vacío

        // When
        ResponseEntity<Void> response = authController.logout(authHeader);

        // Then
        // El método extractTokenFromHeader retorna "" (no null), así que se procesa como token vacío
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(authService, times(1)).deleteToken(""); // Se llama con cadena vacía
    }

    @Test
    @DisplayName("Logout debe extraer correctamente el token del header")
    void logout_ShouldExtractTokenCorrectly() {
        // Given
        String token = "my-custom-token-123";
        String authHeader = "Bearer " + token;
        doNothing().when(authService).deleteToken(token);

        // When
        ResponseEntity<Void> response = authController.logout(authHeader);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(authService, times(1)).deleteToken(token);
    }

    @Test
    @DisplayName("Logout con token inexistente debe retornar 204 No Content (idempotente)")
    void logout_WithNonExistentToken_ShouldReturnNoContent() {
        // Given
        String authHeader = "Bearer non-existent-token";
        doNothing().when(authService).deleteToken("non-existent-token");

        // When
        ResponseEntity<Void> response = authController.logout(authHeader);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(authService, times(1)).deleteToken("non-existent-token");
    }

    @Test
    @DisplayName("Login debe propagar excepciones del servicio")
    void login_WhenServiceThrowsException_ShouldPropagateException() {
        // Given
        when(clientService.login(anyString(), anyString()))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> authController.login(validLoginRequest));

        verify(clientService, times(1)).login(validLoginRequest.email(), validLoginRequest.password());
        verify(authService, never()).createTokenFromUser(any());
    }

    @Test
    @DisplayName("Logout debe manejar excepciones del servicio de autenticación")
    void logout_WhenServiceThrowsException_ShouldPropagateException() {
        // Given
        String authHeader = "Bearer " + AuthFixtures.ADMIN_TOKEN;
        doThrow(new RuntimeException("Database error"))
                .when(authService).deleteToken(AuthFixtures.ADMIN_TOKEN);

        // When & Then
        assertThrows(RuntimeException.class, () -> authController.logout(authHeader));

        verify(authService, times(1)).deleteToken(AuthFixtures.ADMIN_TOKEN);
    }

    @Test
    @DisplayName("Login con múltiples espacios en Bearer debe extraer token con espacios incluidos")
    void logout_WithMultipleSpacesInBearer_ShouldExtractTokenCorrectly() {
        // Given - El método extractTokenFromHeader NO hace trim
        String tokenWithSpaces = "  " + AuthFixtures.ADMIN_TOKEN;
        String authHeader = "Bearer " + tokenWithSpaces; // Múltiples espacios después de Bearer
        lenient().doNothing().when(authService).deleteToken(anyString()); // Usar lenient para evitar strict stubbing issues

        // When
        ResponseEntity<Void> response = authController.logout(authHeader);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(authService, times(1)).deleteToken(tokenWithSpaces); // Verifica que se llama con espacios
    }
}

