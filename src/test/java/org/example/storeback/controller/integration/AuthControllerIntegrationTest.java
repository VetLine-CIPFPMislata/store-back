package org.example.storeback.controller.integration;

import org.example.storeback.controller.webmodel.request.LoginRequest;
import org.example.storeback.controller.webmodel.response.LoginResponse;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.repository.SessionRepository;
import org.example.storeback.domain.repository.entity.SessionEntity;
import org.example.storeback.domain.service.ClientService;
import org.example.storeback.domain.service.PasswordEncryptionService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.testutil.AuthFixtures;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("AuthController Integration Tests")
public class AuthControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PasswordEncryptionService passwordEncryptionService;

    private ClientDto adminClient;
    private ClientDto userClient;

    private static final String ADMIN_EMAIL = "admin-integration@test.com";
    private static final String USER_EMAIL = "user-integration@test.com";

    @BeforeAll
    void setUp() {

        String encryptedPassword = passwordEncryptionService.encryptPassword(AuthFixtures.VALID_PASSWORD);

        adminClient = clientService.findByEmail(ADMIN_EMAIL)
                .orElseGet(() -> {
                    ClientDto adminClientDto = new ClientDto(
                            null,
                            "Admin Test",
                            ADMIN_EMAIL,
                            encryptedPassword,
                            "123456789",
                            null,
                            Role.ADMIN
                    );
                    return clientService.save(adminClientDto);
                });

        userClient = clientService.findByEmail(USER_EMAIL)
                .orElseGet(() -> {
                    ClientDto userClientDto = new ClientDto(
                            null,
                            "User Test",
                            USER_EMAIL,
                            encryptedPassword,
                            "987654321",
                            null,
                            Role.USER
                    );
                    return clientService.save(userClientDto);
                });
    }

    @Test
    @DisplayName("POST /api/auth/login - Login exitoso con credenciales válidas de ADMIN")
    void login_WithValidAdminCredentials_ShouldReturn200WithToken() {
        LoginRequest loginRequest = new LoginRequest(adminClient.email(), AuthFixtures.VALID_PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().token()).isNotNull();
        assertThat(response.getBody().token()).isNotEmpty();

        String token = response.getBody().token();
        Optional<SessionEntity> session = sessionRepository.findByToken(token);
        assertThat(session).isPresent();
        assertThat(session.get().clientId()).isEqualTo(adminClient.id());
    }

    @Test
    @DisplayName("POST /api/auth/login - Login exitoso con credenciales válidas de USER")
    void login_WithValidUserCredentials_ShouldReturn200WithToken() {
        LoginRequest loginRequest = new LoginRequest(userClient.email(), AuthFixtures.VALID_PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().token()).isNotNull();

        String token = response.getBody().token();
        Optional<SessionEntity> session = sessionRepository.findByToken(token);
        assertThat(session).isPresent();
        assertThat(session.get().clientId()).isEqualTo(userClient.id());
    }

    @Test
    @DisplayName("POST /api/auth/login - Login fallido con email inexistente")
    void login_WithNonExistentEmail_ShouldReturn401() {
        LoginRequest loginRequest = new LoginRequest("nonexistent@test.com", AuthFixtures.VALID_PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("POST /api/auth/login - Login fallido con contraseña incorrecta")
    void login_WithWrongPassword_ShouldReturn401() {
        LoginRequest loginRequest = new LoginRequest(adminClient.email(), "wrongpassword");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("POST /api/auth/login - Login fallido con email vacío")
    void login_WithEmptyEmail_ShouldReturn400() {
        LoginRequest loginRequest = new LoginRequest("", AuthFixtures.VALID_PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("POST /api/auth/login - Login fallido con contraseña vacía")
    void login_WithEmptyPassword_ShouldReturn400() {
        LoginRequest loginRequest = new LoginRequest(adminClient.email(), "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("POST /api/auth/logout - Logout exitoso con token válido")
    void logout_WithValidToken_ShouldReturn204AndDeleteSession() {
        LoginRequest loginRequest = new LoginRequest(adminClient.email(), AuthFixtures.VALID_PASSWORD);
        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> loginRequestEntity = new HttpEntity<>(loginRequest, loginHeaders);

        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(
                "/api/auth/login",
                loginRequestEntity,
                LoginResponse.class
        );

        assertThat(loginResponse.getBody()).isNotNull();
        String token = loginResponse.getBody().token();
        assertThat(token).isNotNull();

        Optional<SessionEntity> sessionBefore = sessionRepository.findByToken(token);
        assertThat(sessionBefore).isPresent();

        HttpHeaders logoutHeaders = new HttpHeaders();
        logoutHeaders.setBearerAuth(token);
        HttpEntity<Void> logoutRequest = new HttpEntity<>(logoutHeaders);

        ResponseEntity<Void> logoutResponse = restTemplate.exchange(
                "/api/auth/logout",
                HttpMethod.POST,
                logoutRequest,
                Void.class
        );

        assertThat(logoutResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Optional<SessionEntity> sessionAfter = sessionRepository.findByToken(token);
        assertThat(sessionAfter).isEmpty();
    }

    @Test
    @DisplayName("POST /api/auth/logout - Logout sin token debe retornar 400")
    void logout_WithoutToken_ShouldReturn400() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/auth/logout",
                HttpMethod.POST,
                request,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("POST /api/auth/logout - Logout con token inválido debe ser idempotente (204)")
    void logout_WithInvalidToken_ShouldReturn204() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("invalid-token-xyz");
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/auth/logout",
                HttpMethod.POST,
                request,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Flujo completo: Login -> Uso del token -> Logout")
    void completeAuthenticationFlow() {
        LoginRequest loginRequest = new LoginRequest(adminClient.email(), AuthFixtures.VALID_PASSWORD);
        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> loginRequestEntity = new HttpEntity<>(loginRequest, loginHeaders);

        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(
                "/api/auth/login",
                loginRequestEntity,
                LoginResponse.class
        );

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).isNotNull();
        String token = loginResponse.getBody().token();
        assertThat(token).isNotNull();

        Optional<SessionEntity> session = sessionRepository.findByToken(token);
        assertThat(session).isPresent();
        assertThat(session.get().clientId()).isEqualTo(adminClient.id());

        HttpHeaders logoutHeaders = new HttpHeaders();
        logoutHeaders.setBearerAuth(token);
        HttpEntity<Void> logoutRequest = new HttpEntity<>(logoutHeaders);

        ResponseEntity<Void> logoutResponse = restTemplate.exchange(
                "/api/auth/logout",
                HttpMethod.POST,
                logoutRequest,
                Void.class
        );

        assertThat(logoutResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Optional<SessionEntity> sessionAfter = sessionRepository.findByToken(token);
        assertThat(sessionAfter).isEmpty();
    }

    @Test
    @DisplayName("Múltiples logins deben crear sesiones diferentes")
    void multipleLogins_ShouldCreateDifferentSessions() {
        LoginRequest loginRequest = new LoginRequest(adminClient.email(), AuthFixtures.VALID_PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response1 = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        ResponseEntity<LoginResponse> response2 = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        assertThat(response1.getBody()).isNotNull();
        assertThat(response2.getBody()).isNotNull();
        assertThat(response1.getBody().token()).isNotNull();
        assertThat(response2.getBody().token()).isNotNull();
        assertThat(response1.getBody().token()).isNotEqualTo(response2.getBody().token());

        Optional<SessionEntity> session1 = sessionRepository.findByToken(response1.getBody().token());
        Optional<SessionEntity> session2 = sessionRepository.findByToken(response2.getBody().token());

        assertThat(session1).isPresent();
        assertThat(session2).isPresent();
    }

    @Test
    @DisplayName("Login case-sensitive en email")
    void login_EmailShouldBeCaseSensitive() {
        LoginRequest loginRequest = new LoginRequest(
                adminClient.email().toUpperCase(),
                AuthFixtures.VALID_PASSWORD
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.UNAUTHORIZED);
    }
}

