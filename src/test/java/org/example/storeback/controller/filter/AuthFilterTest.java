package org.example.storeback.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.service.AuthService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.domain.validation.RequiresRole;
import org.example.storeback.testutil.AuthFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthFilter Tests")
class AuthFilterTest {

    @Mock
    private AuthService authService;

    @Mock
    private RequestMappingHandlerMapping handlerMapping;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HandlerMethod handlerMethod;

    @Mock
    private PrintWriter writer;

    @InjectMocks
    private AuthFilter authFilter;

    private ClientDto adminClient;
    private ClientDto userClient;

    @BeforeEach
    void setUp() throws IOException {
        adminClient = AuthFixtures.createAdminClientDto();
        userClient = AuthFixtures.createUserClientDto();

        // Mock PrintWriter para todos los tests (lenient porque no todos los tests lo usan)
        lenient().when(response.getWriter()).thenReturn(writer);
    }

    @Test
    @DisplayName("Debe permitir acceso a /api/auth/login sin autenticación")
    void doFilterInternal_WithLoginEndpoint_ShouldAllowAccess() throws ServletException, IOException {
        // Given
        when(request.getRequestURI()).thenReturn("/api/auth/login");

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, times(1)).doFilter(request, response);
        verify(authService, never()).getUserFromToken(anyString());
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    @DisplayName("Debe permitir acceso a endpoint sin @RequiresRole")
    void doFilterInternal_WithoutRequiresRole_ShouldAllowAccess() throws Exception {
        // Given
        when(request.getRequestURI()).thenReturn("/api/products");
        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(null);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, times(1)).doFilter(request, response);
        verify(authService, never()).getUserFromToken(anyString());
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    @DisplayName("Debe denegar acceso cuando falta el header Authorization en endpoint protegido")
    void doFilterInternal_WithRequiresRoleButNoAuthHeader_ShouldReturn401() throws Exception {
        // Given
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn(null);

        RequiresRole requiresRole = mock(RequiresRole.class);
        lenient().when(requiresRole.value()).thenReturn(Role.ADMIN); // lenient porque puede que no se llame

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
        verify(authService, never()).getUserFromToken(anyString());
    }

    @Test
    @DisplayName("Debe denegar acceso cuando el header Authorization no tiene formato Bearer")
    void doFilterInternal_WithInvalidAuthHeaderFormat_ShouldReturn401() throws Exception {
        // Given
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn("InvalidFormat token");

        RequiresRole requiresRole = mock(RequiresRole.class);
        lenient().when(requiresRole.value()).thenReturn(Role.ADMIN); // lenient porque puede que no se llame

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
        verify(authService, never()).getUserFromToken(anyString());
    }

    @Test
    @DisplayName("Debe denegar acceso cuando el token es inválido")
    void doFilterInternal_WithInvalidToken_ShouldReturn401() throws Exception {
        // Given
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(authService.getUserFromToken("invalid-token")).thenReturn(Optional.empty());

        RequiresRole requiresRole = mock(RequiresRole.class);
        lenient().when(requiresRole.value()).thenReturn(Role.ADMIN); // lenient porque puede que no se llame

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
        verify(authService, times(1)).getUserFromToken("invalid-token");
    }

    @Test
    @DisplayName("Debe denegar acceso cuando el usuario no tiene el rol requerido")
    void doFilterInternal_WithInsufficientRole_ShouldReturn403() throws Exception {
        // Given
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + AuthFixtures.USER_TOKEN);
        when(authService.getUserFromToken(AuthFixtures.USER_TOKEN)).thenReturn(Optional.of(userClient));

        RequiresRole requiresRole = mock(RequiresRole.class);
        when(requiresRole.value()).thenReturn(Role.ADMIN);

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(response, times(1)).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(filterChain, never()).doFilter(request, response);
        verify(authService, times(1)).getUserFromToken(AuthFixtures.USER_TOKEN);
    }

    @Test
    @DisplayName("Debe permitir acceso cuando el usuario tiene el rol requerido ADMIN")
    void doFilterInternal_WithAdminRole_ShouldAllowAccess() throws Exception {
        // Given
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + AuthFixtures.ADMIN_TOKEN);
        when(authService.getUserFromToken(AuthFixtures.ADMIN_TOKEN)).thenReturn(Optional.of(adminClient));

        RequiresRole requiresRole = mock(RequiresRole.class);
        when(requiresRole.value()).thenReturn(Role.ADMIN);

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, times(1)).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
        verify(authService, times(1)).getUserFromToken(AuthFixtures.ADMIN_TOKEN);
    }

    @Test
    @DisplayName("Debe permitir acceso cuando el usuario tiene el rol requerido USER")
    void doFilterInternal_WithUserRole_ShouldAllowAccess() throws Exception {
        // Given
        when(request.getRequestURI()).thenReturn("/api/orders");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + AuthFixtures.USER_TOKEN);
        when(authService.getUserFromToken(AuthFixtures.USER_TOKEN)).thenReturn(Optional.of(userClient));

        RequiresRole requiresRole = mock(RequiresRole.class);
        when(requiresRole.value()).thenReturn(Role.USER);

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, times(1)).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
        verify(authService, times(1)).getUserFromToken(AuthFixtures.USER_TOKEN);
    }

    @Test
    @DisplayName("Debe extraer correctamente el token del header Authorization")
    void doFilterInternal_ShouldExtractTokenCorrectly() throws Exception {
        // Given
        String customToken = "my-custom-token-xyz";
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + customToken);
        when(authService.getUserFromToken(customToken)).thenReturn(Optional.of(adminClient));

        RequiresRole requiresRole = mock(RequiresRole.class);
        when(requiresRole.value()).thenReturn(Role.ADMIN);

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(authService, times(1)).getUserFromToken(customToken);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("Debe manejar correctamente token con espacios adicionales - Token será inválido")
    void doFilterInternal_WithExtraSpacesInToken_ShouldRejectAsInvalid() throws Exception {
        // Given - El filtro NO hace trim, así que "  token" será tratado como token diferente
        String tokenWithSpaces = "  " + AuthFixtures.ADMIN_TOKEN;
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenWithSpaces);
        when(authService.getUserFromToken(tokenWithSpaces)).thenReturn(Optional.empty()); // Token no encontrado

        RequiresRole requiresRole = mock(RequiresRole.class);
        lenient().when(requiresRole.value()).thenReturn(Role.ADMIN); // lenient porque puede que no se llame

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then - El token con espacios no se encuentra, así que debe retornar 401
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    @DisplayName("Debe denegar acceso cuando el header Bearer está vacío")
    void doFilterInternal_WithEmptyBearerToken_ShouldReturn401() throws Exception {
        // Given
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn("Bearer ");
        when(authService.getUserFromToken("")).thenReturn(Optional.empty()); // Token vacío no existe

        RequiresRole requiresRole = mock(RequiresRole.class);
        lenient().when(requiresRole.value()).thenReturn(Role.ADMIN);

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
        verify(authService, times(1)).getUserFromToken(""); // SÍ se llama con cadena vacía
    }

    @Test
    @DisplayName("Debe manejar excepciones al obtener el handler con error 500")
    void doFilterInternal_WhenHandlerThrowsException_ShouldReturn500() throws Exception {
        // Given
        when(request.getRequestURI()).thenReturn("/api/products");
        when(handlerMapping.getHandler(request)).thenThrow(new RuntimeException("Handler error"));

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then - El filtro captura la excepción y retorna 500
        verify(response, times(1)).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(filterChain, never()).doFilter(request, response);
        verify(authService, never()).getUserFromToken(anyString());
    }

    @Test
    @DisplayName("Debe permitir múltiples llamadas con diferentes tokens - Admin permite acceso")
    void doFilterInternal_WithDifferentTokens_AdminShouldAllow() throws Exception {
        // Primera llamada con admin
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + AuthFixtures.ADMIN_TOKEN);
        when(authService.getUserFromToken(AuthFixtures.ADMIN_TOKEN)).thenReturn(Optional.of(adminClient));

        RequiresRole requiresRole = mock(RequiresRole.class);
        when(requiresRole.value()).thenReturn(Role.ADMIN);

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(authService, times(1)).getUserFromToken(AuthFixtures.ADMIN_TOKEN);
        verify(filterChain, times(1)).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    @DisplayName("Usuario USER no debe poder acceder a endpoints que requieren ADMIN (403)")
    void doFilterInternal_WithUserRoleOnAdminEndpoint_ShouldReturn403() throws Exception {
        // Llamada con user a endpoint que requiere ADMIN
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + AuthFixtures.USER_TOKEN);
        when(authService.getUserFromToken(AuthFixtures.USER_TOKEN)).thenReturn(Optional.of(userClient));

        RequiresRole requiresRole = mock(RequiresRole.class);
        when(requiresRole.value()).thenReturn(Role.ADMIN); // Requiere ADMIN pero usuario es USER

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(authService, times(1)).getUserFromToken(AuthFixtures.USER_TOKEN);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    @DisplayName("Debe ser case-sensitive con 'Bearer' - 'bearer' minúscula debe fallar")
    void doFilterInternal_WithLowercaseBearer_ShouldFail() throws Exception {
        // Given
        when(request.getRequestURI()).thenReturn("/api/products");
        when(request.getHeader("Authorization")).thenReturn("bearer " + AuthFixtures.ADMIN_TOKEN); // lowercase

        RequiresRole requiresRole = mock(RequiresRole.class);
        lenient().when(requiresRole.value()).thenReturn(Role.ADMIN); // lenient porque puede que no se llame

        when(handlerMapping.getHandler(request)).thenReturn(
            new org.springframework.web.servlet.HandlerExecutionChain(handlerMethod)
        );
        when(handlerMethod.getMethodAnnotation(RequiresRole.class)).thenReturn(requiresRole);

        // When
        authFilter.doFilterInternal(request, response, filterChain);

        // Then - Debe fallar porque 'Bearer' es case-sensitive
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(authService, never()).getUserFromToken(anyString());
        verify(filterChain, never()).doFilter(request, response);
    }
}

