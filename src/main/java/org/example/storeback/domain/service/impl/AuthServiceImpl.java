package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.mappers.ClientMapper;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.repository.ClientRepository;
import org.example.storeback.domain.repository.SessionRepository;
import org.example.storeback.domain.repository.entity.SessionEntity;
import org.example.storeback.domain.service.AuthService;
import org.example.storeback.domain.service.dto.ClientDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class AuthServiceImpl implements AuthService {

    private final SessionRepository sessionRepository;
    private final ClientRepository clientRepository;

    public AuthServiceImpl(SessionRepository sessionRepository, ClientRepository clientRepository) {
        this.sessionRepository = sessionRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public String createTokenFromUser(ClientDto clientDto) {
        String token = UUID.randomUUID().toString();

        SessionEntity sessionEntity = new SessionEntity(
                null,
                clientDto.id(),
                token,
                LocalDateTime.now()
        );

        sessionRepository.save(sessionEntity);

        return token;
    }

    @Override
    public Optional<ClientDto> getUserFromToken(String token) {
        return sessionRepository.findByToken(token)
                .flatMap(session -> clientRepository.findById(session.clientId()))
                .map(ClientMapper.getInstance()::fromClientEntityToClient)
                .map(ClientMapper.getInstance()::fromClientToClientDto)
                .filter(clientDto -> clientDto.role() == Role.ADMIN); // Solo devuelve si es ADMIN
    }

    @Override
    public Optional<ClientDto> getAnyUserFromToken(String token) {
        return sessionRepository.findByToken(token)
                .flatMap(session -> clientRepository.findById(session.clientId()))
                .map(ClientMapper.getInstance()::fromClientEntityToClient)
                .map(ClientMapper.getInstance()::fromClientToClientDto);

    }

    @Override
    public void deleteToken(String token) {
        sessionRepository.deleteByToken(token);
    }
}
