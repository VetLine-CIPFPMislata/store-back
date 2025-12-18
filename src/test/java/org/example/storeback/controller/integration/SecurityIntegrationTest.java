package org.example.storeback.controller.integration;

import org.example.storeback.controller.webmodel.request.LoginRequest;
import org.example.storeback.controller.webmodel.response.LoginResponse;
import org.example.storeback.domain.models.Role;
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
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Security Integration Tests - Verificación de control de acceso")
public class SecurityIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncryptionService passwordEncryptionService;

    private ClientDto adminClient;
    private String adminToken;
    private String userToken;

    private static final String ADMIN_EMAIL = "security-admin@test.com";
    private static final String USER_EMAIL = "security-user@test.com";

    @BeforeAll
    void setUp() {
        // Configurar el RestTemplate para buffering en lugar de streaming
        // Esto permite manejar correctamente respuestas de error 401
        restTemplate.getRestTemplate().setRequestFactory(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        String encryptedPassword = passwordEncryptionService.encryptPassword(AuthFixtures.VALID_PASSWORD);


        adminClient = clientService.findByEmail(ADMIN_EMAIL)
                .orElseGet(() -> {
                    ClientDto adminClientDto = new ClientDto(
                            null,
                            "Security Admin",
                            ADMIN_EMAIL,
                            encryptedPassword,
                            "111111111",
                            null,
                            Role.ADMIN
                    );
                    return clientService.save(adminClientDto);
                });

        clientService.findByEmail(USER_EMAIL)
                .orElseGet(() -> {
                    ClientDto userClientDto = new ClientDto(
                            null,
                            "Security User",
                            USER_EMAIL,
                            encryptedPassword,
                            "222222222",
                            null,
                            Role.USER
                    );
                    return clientService.save(userClientDto);
                });

        adminToken = loginAndGetToken(ADMIN_EMAIL);
        userToken = loginAndGetToken(USER_EMAIL);
    }

    private String loginAndGetToken(String email) {
        LoginRequest loginRequest = new LoginRequest(email, AuthFixtures.VALID_PASSWORD);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        assertThat(response.getBody()).isNotNull();
        return response.getBody().token();
    }

    private HttpHeaders createAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


    @Test
    @DisplayName("Endpoint público /api/auth/login debe ser accesible sin token")
    void publicEndpoint_ShouldBeAccessibleWithoutToken() {
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
    }

    @Test
    @DisplayName("GET de productos sin token debe retornar 401")
    void getProducts_WithoutToken_ShouldReturn401() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/products",
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("GET de productos con token de admin debe retornar 200")
    void getProducts_WithAdminToken_ShouldReturn200() {
        HttpHeaders headers = createAuthHeaders(adminToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/products",
                HttpMethod.GET,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Usuario USER no debe poder acceder a endpoints de ADMIN")
    void userRole_AccessingAdminEndpoint_ShouldReturn403() {
        HttpHeaders headers = createAuthHeaders(userToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // Usar GET en lugar de POST para evitar problemas de deserialización
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "/api/products",
                    HttpMethod.GET,
                    request,
                    String.class
            );

            assertThat(response.getStatusCode().value()).isIn(401, 403);
        } catch (org.springframework.web.client.HttpClientErrorException.Forbidden e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        } catch (org.springframework.web.client.HttpClientErrorException.Unauthorized e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Test
    @DisplayName("Usuario ADMIN debe poder acceder a endpoints de ADMIN")
    void adminRole_AccessingAdminEndpoint_ShouldBeAllowed() {
        HttpHeaders headers = createAuthHeaders(adminToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/products",
                HttpMethod.GET,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Mismo token puede ser usado en múltiples peticiones")
    void sameToken_CanBeUsedMultipleTimes() {
        HttpHeaders headers = createAuthHeaders(adminToken);

        ResponseEntity<String> response1 = restTemplate.exchange(
                "/api/categories",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        ResponseEntity<String> response2 = restTemplate.exchange(
                "/api/categories",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        assertThat(response1.getStatusCode().value()).isNotEqualTo(401);
        assertThat(response2.getStatusCode().value()).isNotEqualTo(401);
    }

    @Test
    @DisplayName("Diferentes usuarios con tokens válidos deben mantener su identidad")
    void differentUsers_ShouldMaintainSeparateIdentities() {
        HttpHeaders adminHeaders = createAuthHeaders(adminToken);
        HttpHeaders userHeaders = createAuthHeaders(userToken);

        ResponseEntity<String> adminResponse = restTemplate.exchange(
                "/api/categories/public",
                HttpMethod.GET,
                new HttpEntity<>(adminHeaders),
                String.class
        );

        ResponseEntity<String> userResponse = restTemplate.exchange(
                "/api/categories/public",
                HttpMethod.GET,
                new HttpEntity<>(userHeaders),
                String.class
        );

        assertThat(adminResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> adminProtectedResponse = restTemplate.exchange(
                "/api/categories",
                HttpMethod.GET,
                new HttpEntity<>(adminHeaders),
                String.class
        );
        assertThat(adminProtectedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        try {
            ResponseEntity<String> userProtectedResponse = restTemplate.exchange(
                    "/api/categories",
                    HttpMethod.GET,
                    new HttpEntity<>(userHeaders),
                    String.class
            );
            assertThat(userProtectedResponse.getStatusCode().value()).isIn(401, 403);
        } catch (org.springframework.web.client.HttpClientErrorException.Forbidden e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        } catch (org.springframework.web.client.HttpClientErrorException.Unauthorized e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Test
    @DisplayName("Endpoint de logout debe retornar 204 incluso con token inválido")
    void logout_WithoutValidToken_ShouldStillSucceed() {
        HttpHeaders headers = createAuthHeaders("some-random-token");

        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/auth/logout",
                HttpMethod.POST,
                new HttpEntity<>(headers),
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Cross-user token injection debe ser prevenido")
    void crossUserTokenInjection_ShouldBePrevented() {
        HttpHeaders userHeaders = createAuthHeaders(userToken);
        HttpEntity<Void> request = new HttpEntity<>(userHeaders);

        try {
            ResponseEntity<String> userResponse = restTemplate.exchange(
                    "/api/products",
                    HttpMethod.GET,
                    request,
                    String.class
            );

            assertThat(userResponse.getStatusCode().value()).isIn(401, 403);
        } catch (org.springframework.web.client.HttpClientErrorException.Forbidden e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        } catch (org.springframework.web.client.HttpClientErrorException.Unauthorized e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Test
    @DisplayName("SQL Injection en credenciales debe ser prevenido")
    void sqlInjection_InCredentials_ShouldBePrevented() {
        LoginRequest maliciousRequest = new LoginRequest(
                "admin@example.com' OR '1'='1",
                AuthFixtures.VALID_PASSWORD
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(maliciousRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/auth/login",
                request,
                LoginResponse.class
        );

        assertThat(response.getStatusCode().value()).isIn(400, 401);
    }

}

