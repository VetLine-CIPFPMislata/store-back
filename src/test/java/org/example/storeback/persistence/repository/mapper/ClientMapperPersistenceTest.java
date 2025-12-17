package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.persistence.dao.jpa.entity.ClientJpaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMapperPersistenceTest {

    @Test
    void fromClientJpaEntityToClientEntity_mapsAllFields() {
        ClientJpaEntity jpa = new ClientJpaEntity(
                1L,
                "Nombre Test",
                "test@example.com",
                "secret",
                "+34123456789",
                42L,
                Role.USER
        );

        ClientEntity entity = ClientMapperPersistence.getInstance().fromClientJpaEntityToClientEntity(jpa);

        assertNotNull(entity);
        assertEquals(jpa.getId(), entity.id());
        assertEquals(jpa.getName(), entity.name());
        assertEquals(jpa.getEmail(), entity.email());
        assertEquals(jpa.getPassword(), entity.password());
        assertEquals(jpa.getPhone(), entity.phone());
        assertEquals(jpa.getCartId(), entity.cartId());
        assertEquals(jpa.getRole(), entity.role());
    }

    @Test
    void fromClientEntityToClientJpaEntity_mapsAllFields() {
        ClientEntity entity = new ClientEntity(
                2L,
                "Otro Nombre",
                "otro@example.com",
                "hash",
                null,
                null,
                Role.ADMIN
        );

        ClientJpaEntity jpa = ClientMapperPersistence.getInstance().fromClientEntityToClientJpaEntity(entity);

        assertNotNull(jpa);
        assertEquals(entity.id(), jpa.getId());
        assertEquals(entity.name(), jpa.getName());
        assertEquals(entity.email(), jpa.getEmail());
        assertEquals(entity.password(), jpa.getPassword());
        assertEquals(entity.phone(), jpa.getPhone());
        assertEquals(entity.cartId(), jpa.getCartId());
        assertEquals(entity.role(), jpa.getRole());
    }

}
