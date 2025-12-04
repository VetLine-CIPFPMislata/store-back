package org.example.storeback.domain.repository;


import org.example.storeback.domain.repository.entity.ClientEntity;

import java.util.Optional;

public interface ClientRepository {
    Optional<ClientEntity>findById(Long id);
    Optional<ClientEntity>findByEmail(String email);
    ClientEntity save(ClientEntity clientEntity);
    void deleteById(Long id);
    boolean existsByEmail(String email);

}
