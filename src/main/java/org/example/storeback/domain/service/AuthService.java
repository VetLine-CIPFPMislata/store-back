package org.example.storeback.domain.service;

import org.example.storeback.domain.service.dto.ClientDto;

import java.util.Optional;

public interface AuthService {
    String createTokenFromUser(ClientDto clientDto);
    Optional<ClientDto> getUserFromToken(String token);
    Optional<ClientDto> getAnyUserFromToken(String token);
    void deleteToken(String token);
}
