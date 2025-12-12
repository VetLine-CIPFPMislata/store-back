package org.example.storeback.domain.service;

import org.example.storeback.domain.service.dto.ClientDto;

import java.util.Optional;

public interface ClientService {
    Optional<ClientDto> findById(Long id);
    Optional<ClientDto>findByEmail(String email);
    ClientDto save(ClientDto clientDto);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    Optional<ClientDto> login(String email, String password);
}
