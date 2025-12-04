package org.example.storeback.domain.service;

import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.domain.service.dto.LoginDto;

import java.util.Optional;

public interface ClientService {
    Optional<ClientDto> findById(Long id);
    Optional<ClientDto>findByEmail(String email);
    ClientDto save(ClientDto clientDto);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    Optional<LoginDto> login(String email, String password);
}
