package org.example.storeback.persistence.repository;

import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.repository.ClientRepository;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ClientRepositoryImplTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testFindById() {
        Long existingId = 1L;

        Optional<ClientEntity> result = clientRepository.findById(existingId);

        assertTrue(result.isPresent());
        assertEquals(existingId, result.get().id());
    }

    @Test
    void testFindByEmail() {
        String existingEmail = "juan.perez@email.com";

        Optional<ClientEntity> result = clientRepository.findByEmail(existingEmail);

        assertTrue(result.isPresent());
        assertEquals(existingEmail, result.get().email());
    }

    @Test
    void testSaveNewClient() {
        ClientEntity newClient = new ClientEntity(
                null,
                "Repository Test User",
                "repotest@example.com",
                "$2a$10$hashedPassword",
                "+34600888999",
                null,
                Role.USER
        );

        ClientEntity saved = clientRepository.save(newClient);

        assertNotNull(saved);
        assertNotNull(saved.id());
        assertEquals("Repository Test User", saved.name());

        Optional<ClientEntity> found = clientRepository.findById(saved.id());
        assertTrue(found.isPresent());
    }

    @Test
    void testUpdateClient() {
        Optional<ClientEntity> existing = clientRepository.findById(1L);
        assertTrue(existing.isPresent());

        ClientEntity clientToUpdate = new ClientEntity(
                existing.get().id(),
                "Updated Name Repository",
                existing.get().email(),
                existing.get().password(),
                "+34600222222",
                existing.get().cartId(),
                existing.get().role()
        );

        ClientEntity updated = clientRepository.save(clientToUpdate);

        assertEquals("Updated Name Repository", updated.name());
        assertEquals("+34600222222", updated.phone());
    }

    @Test
    void testDeleteById() {
        ClientEntity newClient = new ClientEntity(
                null,
                "To Delete Repo",
                "todelrepo@example.com",
                "password",
                "+34600333444",
                null,
                Role.USER
        );
        ClientEntity saved = clientRepository.save(newClient);

        clientRepository.deleteById(saved.id());

        Optional<ClientEntity> found = clientRepository.findById(saved.id());
        assertFalse(found.isPresent());
    }

    @Test
    void testExistsByEmail() {
        String existingEmail = "juan.perez@email.com";

        boolean exists = clientRepository.existsByEmail(existingEmail);

        assertTrue(exists);
    }

}
