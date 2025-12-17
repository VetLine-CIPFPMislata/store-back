package org.example.storeback.persistence.dao.jpa.impl;

import org.example.storeback.config.TestConfig;
import org.example.storeback.domain.models.Role;
import org.example.storeback.persistence.dao.ClientJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.ClientJpaEntity;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
class ClientJpaDaoImplTest {

    @Autowired
    private ClientJpaDao clientJpaDao;

    @Test
    void testFindAll() {
        List<ClientEntity> clients = clientJpaDao.findAll();

        assertNotNull(clients);
        assertFalse(clients.isEmpty());
    }

    @Test
    void testFindById() {
        Long existingId = 1L;

        Optional<ClientEntity> result = clientJpaDao.findById(existingId);

        assertTrue(result.isPresent());
        assertEquals(existingId, result.get().id());
    }

    @Test
    void testFindByIdNotFound() {
        Long nonExistingId = 9999L;

        Optional<ClientEntity> result = clientJpaDao.findById(nonExistingId);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindByEmail() {
        String existingEmail = "juan.perez@email.com";

        Optional<ClientEntity> result = clientJpaDao.findByEmail(existingEmail);

        assertTrue(result.isPresent());
        assertEquals(existingEmail, result.get().email());
    }

    @Test
    void testFindByEmailNotFound() {
        String nonExistingEmail = "notfound@example.com";

        Optional<ClientEntity> result = clientJpaDao.findByEmail(nonExistingEmail);

        assertFalse(result.isPresent());
    }

    @Test
    void testSaveNewClient() {
        ClientEntity newClient = new ClientEntity(
                null,
                "Test User",
                "test@example.com",
                "$2a$10$hashedPassword",
                "+34600999888",
                null,
                Role.USER
        );

        ClientEntity saved = clientJpaDao.save(newClient);

        assertNotNull(saved);
        assertNotNull(saved.id());
        assertEquals("Test User", saved.name());
        assertEquals("test@example.com", saved.email());

        Optional<ClientEntity> found = clientJpaDao.findById(saved.id());
        assertTrue(found.isPresent());
    }

    @Test
    void testUpdateClient() {
        Optional<ClientEntity> existing = clientJpaDao.findById(1L);
        assertTrue(existing.isPresent());

        ClientEntity clientToUpdate = new ClientEntity(
                existing.get().id(),
                "Updated Name",
                existing.get().email(),
                existing.get().password(),
                "+34600111111",
                existing.get().cartId(),
                existing.get().role()
        );

        ClientEntity updated = clientJpaDao.save(clientToUpdate);

        assertNotNull(updated);
        assertEquals("Updated Name", updated.name());
        assertEquals("+34600111111", updated.phone());

        Optional<ClientEntity> found = clientJpaDao.findById(updated.id());
        assertTrue(found.isPresent());
        assertEquals("Updated Name", found.get().name());
    }

    @Test
    void testDeleteById() {
        ClientEntity newClient = new ClientEntity(
                null,
                "To Delete",
                "todelete@example.com",
                "password",
                "+34600777888",
                null,
                Role.USER
        );
        ClientEntity saved = clientJpaDao.save(newClient);
        Long idToDelete = saved.id();

        clientJpaDao.deleteById(idToDelete);

        Optional<ClientEntity> found = clientJpaDao.findById(idToDelete);
        assertFalse(found.isPresent());
    }

    @Test
    void testSaveClientWithAdminRole() {
        ClientEntity adminClient = new ClientEntity(
                null,
                "Admin Test",
                "admintest@store.com",
                "$2a$10$hashedPassword",
                "+34600444555",
                null,
                Role.ADMIN
        );

        ClientEntity saved = clientJpaDao.save(adminClient);

        assertNotNull(saved);
        assertEquals(Role.ADMIN, saved.role());
    }
}
