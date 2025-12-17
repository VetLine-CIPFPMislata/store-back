package org.example.storeback.domain.mappers;

import org.example.storeback.domain.models.Client;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.domain.service.dto.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientMapperTest {

    private ClientMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = ClientMapper.getInstance();
    }

    @Test
    void testSingletonInstance() {
        ClientMapper instance1 = ClientMapper.getInstance();
        ClientMapper instance2 = ClientMapper.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    void testFromClientEntityToClient() {
        ClientEntity entity = new ClientEntity(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "password123",
                "+34600111222",
                10L,
                Role.USER
        );

        Client client = mapper.fromClientEntityToClient(entity);

        assertNotNull(client);
        assertEquals(entity.id(), client.getId());
        assertEquals(entity.name(), client.getName());
        assertEquals(entity.email(), client.getEmail());
        assertEquals(entity.password(), client.getPassword());
        assertEquals(entity.phone(), client.getPhone());
        assertEquals(entity.cartId(), client.getCartId());
        assertEquals(entity.role(), client.getRole());
    }

    @Test
    void testFromClientEntityToClientWithNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapper.fromClientEntityToClient(null);
        });
    }

    @Test
    void testFromClientToClientEntity() {
        Client client = new Client(
                1L,
                "María García",
                "maria@example.com",
                "pass123",
                "+34600444555",
                15L,
                Role.ADMIN
        );

        ClientEntity entity = mapper.fromClientToClientEntity(client);

        assertNotNull(entity);
        assertEquals(client.getId(), entity.id());
        assertEquals(client.getName(), entity.name());
        assertEquals(client.getEmail(), entity.email());
        assertEquals(client.getPassword(), entity.password());
        assertEquals(client.getPhone(), entity.phone());
        assertEquals(client.getCartId(), entity.cartId());
        assertEquals(client.getRole(), entity.role());
    }

    @Test
    void testFromClientToClientEntityWithNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mapper.fromClientToClientEntity(null);
        });
    }

    @Test
    void testFromClientDtoToClient() {
        ClientDto dto = new ClientDto(
                1L,
                "Test User",
                "test@example.com",
                "testpass",
                "+34600666777",
                20L,
                Role.USER
        );

        Client client = mapper.fromClientDtoToClient(dto);

        assertNotNull(client);
        assertEquals(dto.id(), client.getId());
        assertEquals(dto.name(), client.getName());
        assertEquals(dto.email(), client.getEmail());
        assertEquals(dto.password(), client.getPassword());
        assertEquals(dto.phone(), client.getPhone());
        assertEquals(dto.cartId(), client.getCartId());
        assertEquals(dto.role(), client.getRole());
    }

    @Test
    void testFromClientToClientDto() {
        Client client = new Client(
                2L,
                "Admin User",
                "admin@store.com",
                "admin123",
                "+34600222333",
                5L,
                Role.ADMIN
        );

        ClientDto dto = mapper.fromClientToClientDto(client);

        assertNotNull(dto);
        assertEquals(client.getId(), dto.id());
        assertEquals(client.getName(), dto.name());
        assertEquals(client.getEmail(), dto.email());
        assertEquals(client.getPassword(), dto.password());
        assertEquals(client.getPhone(), dto.phone());
        assertEquals(client.getCartId(), dto.cartId());
        assertEquals(client.getRole(), dto.role());
    }

    @Test
    void testBidirectionalConversionEntityToClientToEntity() {
        ClientEntity originalEntity = new ClientEntity(
                1L,
                "Test",
                "test@example.com",
                "pass",
                "+34600111222",
                10L,
                Role.USER
        );

        Client client = mapper.fromClientEntityToClient(originalEntity);
        ClientEntity resultEntity = mapper.fromClientToClientEntity(client);

        assertEquals(originalEntity.id(), resultEntity.id());
        assertEquals(originalEntity.name(), resultEntity.name());
        assertEquals(originalEntity.email(), resultEntity.email());
        assertEquals(originalEntity.password(), resultEntity.password());
        assertEquals(originalEntity.phone(), resultEntity.phone());
        assertEquals(originalEntity.cartId(), resultEntity.cartId());
        assertEquals(originalEntity.role(), resultEntity.role());
    }
}

