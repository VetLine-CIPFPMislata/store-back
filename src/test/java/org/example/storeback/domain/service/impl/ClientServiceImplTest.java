package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.repository.ClientRepository;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.domain.service.PasswordEncryptionService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncryptionService passwordEncryptionService;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientEntity testClientEntity;

    @BeforeEach
    void setUp() {
        testClientEntity = new ClientEntity(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "$2a$10$hashedPassword",
                "+34600111222",
                10L,
                Role.USER
        );
    }

    @Test
    void testFindById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClientEntity));

        Optional<ClientDto> result = clientService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testClientEntity.id(), result.get().id());
        assertEquals(testClientEntity.name(), result.get().name());
        verify(clientRepository).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<ClientDto> result = clientService.findById(999L);

        assertFalse(result.isPresent());
        verify(clientRepository).findById(999L);
    }

    @Test
    void testFindByEmail() {
        when(clientRepository.findByEmail("juan@example.com")).thenReturn(Optional.of(testClientEntity));

        Optional<ClientDto> result = clientService.findByEmail("juan@example.com");

        assertTrue(result.isPresent());
        assertEquals(testClientEntity.email(), result.get().email());
        verify(clientRepository).findByEmail("juan@example.com");
    }

    @Test
    void testSaveNewClient() {
        ClientDto newClientDto = new ClientDto(null, "New User", "new@example.com", "password123", "+34600222333", null, Role.USER);
        ClientEntity savedEntity = new ClientEntity(2L, "New User", "new@example.com", "$2a$10$encryptedPassword", "+34600222333", null, Role.USER);

        when(clientRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(savedEntity);
        when(passwordEncryptionService.encryptPassword("password123")).thenReturn("$2a$10$encryptedPassword");


        ClientDto result = clientService.save(newClientDto);

        assertNotNull(result);
        assertEquals(2L, result.id());
        assertEquals("New User", result.name());
        verify(clientRepository).existsByEmail("new@example.com");
        verify(passwordEncryptionService).encryptPassword("password123");
    }

    @Test
    void testSaveExistingClient() {
        ClientDto updateClientDto = new ClientDto(1L, "Updated Name", "juan@example.com", "$2a$10$hashedPassword", "+34600111222", 10L, Role.USER);
        ClientEntity updatedEntity = new ClientEntity(1L, "Updated Name", "juan@example.com", "$2a$10$hashedPassword", "+34600111222", 10L, Role.USER);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClientEntity));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(updatedEntity);

        ClientDto result = clientService.save(updateClientDto);

        assertNotNull(result);
        assertEquals("Updated Name", result.name());
        verify(clientRepository).findById(1L);
    }

    @Test
    void testSaveNewClientWithDuplicateEmailThrowsException() {
        ClientDto newClientDto = new ClientDto(null, "New User", "existing@example.com", "password123", "+34600222333", null, Role.USER);

        when(clientRepository.existsByEmail("existing@example.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            clientService.save(newClientDto);
        });

        verify(clientRepository).existsByEmail("existing@example.com");

    }

    @Test
    void testDeleteById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClientEntity));
        doNothing().when(clientRepository).deleteById(1L);

        clientService.deleteById(1L);

        verify(clientRepository).findById(1L);
        verify(clientRepository).deleteById(1L);
    }

    @Test
    void testDeleteByIdNotFoundThrowsException() {
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            clientService.deleteById(999L);
        });

        verify(clientRepository).findById(999L);
    }

    @Test
    void testLoginSuccess() {
        String email = "juan@example.com";
        String rawPassword = "password123";
        String hashedPassword = "$2a$10$RSb4PBWmInzqayD.dNBVceD3d9ruD11xAqQQrByaHW4EpdahxJhOu";

        ClientEntity clientEntity = new ClientEntity(1L, "Juan Pérez", email, hashedPassword, "+34600111222", 10L, Role.USER);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(clientEntity));
        when(passwordEncryptionService.matches(rawPassword, hashedPassword)).thenReturn(true);

        Optional<ClientDto> result = clientService.login(email, rawPassword);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().email());
        verify(clientRepository).findByEmail(email);
        verify(passwordEncryptionService).matches(rawPassword, hashedPassword);
    }

    @Test
    void testLoginFailureWrongPassword() {
        String email = "juan@example.com";
        String wrongPassword = "wrongpassword";
        String hashedPassword = "$2a$10$RSb4PBWmInzqayD.dNBVceD3d9ruD11xAqQQrByaHW4EpdahxJhOu";

        ClientEntity clientEntity = new ClientEntity(1L, "Juan Pérez", email, hashedPassword, "+34600111222", 10L, Role.USER);
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(clientEntity));
        when(passwordEncryptionService.matches(wrongPassword, hashedPassword)).thenReturn(false);

        Optional<ClientDto> result = clientService.login(email, wrongPassword);

        assertFalse(result.isPresent());
        verify(clientRepository).findByEmail(email);
        verify(passwordEncryptionService).matches(wrongPassword, hashedPassword);
    }

    @Test
    void testLoginFailureUserNotFound() {
        when(clientRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        Optional<ClientDto> result = clientService.login("notfound@example.com", "password123");

        assertFalse(result.isPresent());
        verify(clientRepository).findByEmail("notfound@example.com");
    }

    @Test
    void testExistsByEmail() {
        when(clientRepository.existsByEmail("juan@example.com")).thenReturn(true);

        boolean result = clientService.existsByEmail("juan@example.com");

        assertTrue(result);
        verify(clientRepository).existsByEmail("juan@example.com");
    }
}
