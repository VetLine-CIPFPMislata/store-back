package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.mappers.ClientMapper;
import org.example.storeback.domain.repository.ClientRepository;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.domain.service.ClientService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.domain.service.dto.LoginDto;

import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<ClientDto> findById(Long id) {
        return clientRepository.findById(id)
                .map(ClientMapper.getInstance()::fromClientEntityToClient)
                .map(ClientMapper.getInstance()::fromClientToClientDto);
    }

    @Override
    public Optional<ClientDto> findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .map(ClientMapper.getInstance()::fromClientEntityToClient)
                .map(ClientMapper.getInstance()::fromClientToClientDto);
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        if (clientDto.id() != null && clientRepository.findById(clientDto.id()).isPresent()) {
            throw new IllegalArgumentException("Client with id " + clientDto.id() + " already exists");
        }

        if (clientRepository.existsByEmail(clientDto.email())) {
            throw new IllegalArgumentException("Client with email " + clientDto.email() + " already exists");
        }

        ClientEntity entityToSave = ClientMapper.getInstance()
                .fromClientToClientEntity(
                        ClientMapper.getInstance().fromClientDtoToClient(clientDto)
                );

        ClientEntity savedEntity = clientRepository.save(entityToSave);

        return ClientMapper.getInstance()
                .fromClientToClientDto(
                        ClientMapper.getInstance().fromClientEntityToClient(savedEntity)
                );
    }

    @Override
    public void deleteById(Long id) {
        if (clientRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Client with id " + id + " not found");
        }
        clientRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public Optional<LoginDto> login(String email, String password) {
        return clientRepository.findByEmail(email)
                .filter(client -> client.password().equals(password))
                .map(client -> new LoginDto(client.email(), client.password()));
    }
}
