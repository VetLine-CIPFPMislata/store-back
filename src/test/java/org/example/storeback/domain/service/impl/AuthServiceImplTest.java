package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.repository.ClientRepository;
import org.example.storeback.domain.repository.SessionRepository;
import org.example.storeback.domain.repository.entity.SessionEntity;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.testutil.AuthFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImpl Tests")
class AuthServiceImplTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private ClientDto adminClient;
    private ClientDto userClient;
    private SessionEntity adminSession;

    @BeforeEach
    void setUp() {
        adminClient = AuthFixtures.createAdminClientDto();
        userClient = AuthFixtures.createUserClientDto();
        adminSession = AuthFixtures.createAdminSession();
    }

    @Test
    @DisplayName("Debe crear token y sesión correctamente para un usuario")
    void createTokenFromUser_ShouldCreateSessionAndReturnToken() {
        // Given
        when(sessionRepository.save(any(SessionEntity.class))).thenAnswer(invocation -> {
            SessionEntity session = invocation.getArgument(0);
            return new SessionEntity(1L, session.clientId(), session.token(), session.loginDate());
        });

        // When
        String token = authService.createTokenFromUser(adminClient);

        // Then
        assertNotNull(token, "El token no debe ser null");
        assertFalse(token.isEmpty(), "El token no debe estar vacío");

        // Verificar que se guardó la sesión con los datos correctos
        ArgumentCaptor<SessionEntity> sessionCaptor = ArgumentCaptor.forClass(SessionEntity.class);
        verify(sessionRepository).save(sessionCaptor.capture());

        SessionEntity savedSession = sessionCaptor.getValue();
        assertEquals(adminClient.id(), savedSession.clientId(), "El clientId debe coincidir");
        assertEquals(token, savedSession.token(), "El token debe coincidir");
        assertNotNull(savedSession.loginDate(), "La fecha de creación debe estar establecida");
    }

    @Test
    @DisplayName("Debe generar tokens únicos para múltiples llamadas")
    void createTokenFromUser_ShouldGenerateUniqueTokens() {
        // Given
        when(sessionRepository.save(any(SessionEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        String token1 = authService.createTokenFromUser(adminClient);
        String token2 = authService.createTokenFromUser(adminClient);

        // Then
        assertNotEquals(token1, token2, "Los tokens deben ser únicos");
        verify(sessionRepository, times(2)).save(any(SessionEntity.class));
    }

    @Test
    @DisplayName("Debe obtener usuario desde token válido")
    void getUserFromToken_WithValidToken_ShouldReturnUser() {
        // Given
        when(sessionRepository.findByToken(AuthFixtures.ADMIN_TOKEN)).thenReturn(Optional.of(adminSession));
        when(clientRepository.findById(adminClient.id())).thenReturn(Optional.of(AuthFixtures.createAdminClientEntity()));

        // When
        Optional<ClientDto> result = authService.getUserFromToken(AuthFixtures.ADMIN_TOKEN);

        // Then
        assertTrue(result.isPresent(), "Debe retornar un usuario");
        assertEquals(adminClient.id(), result.get().id());
        assertEquals(adminClient.email(), result.get().email());
        assertEquals(adminClient.role(), result.get().role());

        verify(sessionRepository).findByToken(AuthFixtures.ADMIN_TOKEN);
        verify(clientRepository).findById(adminClient.id());
    }

    @Test
    @DisplayName("Debe retornar vacío cuando el token no existe")
    void getUserFromToken_WithNonExistentToken_ShouldReturnEmpty() {
        // Given
        when(sessionRepository.findByToken(anyString())).thenReturn(Optional.empty());

        // When
        Optional<ClientDto> result = authService.getUserFromToken("non-existent-token");

        // Then
        assertFalse(result.isPresent(), "Debe retornar vacío cuando el token no existe");
        verify(sessionRepository).findByToken("non-existent-token");
        verify(clientRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Debe retornar vacío cuando la sesión existe pero el cliente no")
    void getUserFromToken_WithValidTokenButNoClient_ShouldReturnEmpty() {
        // Given
        when(sessionRepository.findByToken(AuthFixtures.ADMIN_TOKEN)).thenReturn(Optional.of(adminSession));
        when(clientRepository.findById(adminClient.id())).thenReturn(Optional.empty());

        // When
        Optional<ClientDto> result = authService.getUserFromToken(AuthFixtures.ADMIN_TOKEN);

        // Then
        assertFalse(result.isPresent(), "Debe retornar vacío cuando el cliente no existe");
        verify(sessionRepository).findByToken(AuthFixtures.ADMIN_TOKEN);
        verify(clientRepository).findById(adminClient.id());
    }

    @Test
    @DisplayName("Debe retornar vacío cuando el token es null")
    void getUserFromToken_WithNullToken_ShouldReturnEmpty() {
        // Given
        when(sessionRepository.findByToken(null)).thenReturn(Optional.empty());

        // When
        Optional<ClientDto> result = authService.getUserFromToken(null);

        // Then
        assertFalse(result.isPresent(), "Debe retornar vacío cuando el token es null");
        verify(sessionRepository).findByToken(null);
    }

    @Test
    @DisplayName("Debe retornar vacío cuando el token está vacío")
    void getUserFromToken_WithEmptyToken_ShouldReturnEmpty() {
        // Given
        when(sessionRepository.findByToken("")).thenReturn(Optional.empty());

        // When
        Optional<ClientDto> result = authService.getUserFromToken("");

        // Then
        assertFalse(result.isPresent(), "Debe retornar vacío cuando el token está vacío");
        verify(sessionRepository).findByToken("");
    }

    @Test
    @DisplayName("Debe eliminar token correctamente")
    void deleteToken_ShouldDeleteSession() {
        // Given
        String tokenToDelete = AuthFixtures.ADMIN_TOKEN;
        doNothing().when(sessionRepository).deleteByToken(tokenToDelete);

        // When
        authService.deleteToken(tokenToDelete);

        // Then
        verify(sessionRepository).deleteByToken(tokenToDelete);
    }

    @Test
    @DisplayName("Debe manejar eliminación de token inexistente sin errores")
    void deleteToken_WithNonExistentToken_ShouldNotThrowException() {
        // Given
        String tokenToDelete = "non-existent-token";
        doNothing().when(sessionRepository).deleteByToken(tokenToDelete);

        // When & Then
        assertDoesNotThrow(() -> authService.deleteToken(tokenToDelete));
        verify(sessionRepository).deleteByToken(tokenToDelete);
    }

    @Test
    @DisplayName("Debe crear sesión con timestamp actual")
    void createTokenFromUser_ShouldCreateSessionWithCurrentTimestamp() {
        // Given
        LocalDateTime beforeCreation = LocalDateTime.now().minusSeconds(1);
        when(sessionRepository.save(any(SessionEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        authService.createTokenFromUser(adminClient);
        LocalDateTime afterCreation = LocalDateTime.now().plusSeconds(1);

        // Then
        ArgumentCaptor<SessionEntity> sessionCaptor = ArgumentCaptor.forClass(SessionEntity.class);
        verify(sessionRepository).save(sessionCaptor.capture());

        SessionEntity savedSession = sessionCaptor.getValue();
        assertTrue(savedSession.loginDate().isAfter(beforeCreation));
        assertTrue(savedSession.loginDate().isBefore(afterCreation));
    }

    @Test
    @DisplayName("Debe manejar múltiples sesiones para diferentes usuarios")
    void createTokenFromUser_WithDifferentUsers_ShouldCreateDifferentSessions() {
        // Given
        when(sessionRepository.save(any(SessionEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        String adminToken = authService.createTokenFromUser(adminClient);
        String userToken = authService.createTokenFromUser(userClient);

        // Then
        assertNotEquals(adminToken, userToken, "Los tokens deben ser diferentes");

        ArgumentCaptor<SessionEntity> sessionCaptor = ArgumentCaptor.forClass(SessionEntity.class);
        verify(sessionRepository, times(2)).save(sessionCaptor.capture());

        SessionEntity adminSessionSaved = sessionCaptor.getAllValues().get(0);
        SessionEntity userSessionSaved = sessionCaptor.getAllValues().get(1);

        assertEquals(adminClient.id(), adminSessionSaved.clientId());
        assertEquals(userClient.id(), userSessionSaved.clientId());
    }

    @Test
    @DisplayName("Debe obtener correctamente usuarios con diferentes roles")
    void getUserFromToken_WithDifferentRoles_ShouldReturnCorrectUsers() {
        // Given - Admin
        SessionEntity adminSession = AuthFixtures.createAdminSession();
        when(sessionRepository.findByToken(AuthFixtures.ADMIN_TOKEN)).thenReturn(Optional.of(adminSession));
        when(clientRepository.findById(adminClient.id())).thenReturn(Optional.of(AuthFixtures.createAdminClientEntity()));

        // Given - User
        SessionEntity userSession = AuthFixtures.createUserSession();
        when(sessionRepository.findByToken(AuthFixtures.USER_TOKEN)).thenReturn(Optional.of(userSession));
        when(clientRepository.findById(userClient.id())).thenReturn(Optional.of(AuthFixtures.createUserClientEntity()));

        // When
        Optional<ClientDto> adminResult = authService.getUserFromToken(AuthFixtures.ADMIN_TOKEN);
        Optional<ClientDto> userResult = authService.getUserFromToken(AuthFixtures.USER_TOKEN);

        // Then
        assertTrue(adminResult.isPresent());
        assertTrue(userResult.isPresent());

        assertThat(adminResult.get().role()).isEqualTo(org.example.storeback.domain.models.Role.ADMIN);
        assertThat(userResult.get().role()).isEqualTo(org.example.storeback.domain.models.Role.USER);
    }
}

