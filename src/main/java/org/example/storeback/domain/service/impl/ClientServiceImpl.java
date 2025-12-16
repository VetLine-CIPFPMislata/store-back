package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.mappers.ClientMapper;
import org.example.storeback.domain.models.Client;
import org.example.storeback.domain.repository.ClientRepository;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.domain.service.ClientService;
import org.example.storeback.domain.service.PasswordEncryptionService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.domain.service.dto.LoginDto;

import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncryptionService passwordEncryptionService;

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncryptionService passwordEncryptionService) {
        this.clientRepository = clientRepository;
        this.passwordEncryptionService = passwordEncryptionService;
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
        if (clientDto.id() != null) {
            Optional<ClientEntity> existing = clientRepository.findById(clientDto.id());
            if (existing.isEmpty()) {
                throw new IllegalArgumentException("Client with id " + clientDto.id() + " not found");
            }
        } else {
            if (clientRepository.existsByEmail(clientDto.email())) {
                throw new IllegalArgumentException("Client with email " + clientDto.email() + " already exists");
            }
        }

        String rawPassword = clientDto.password();
        String passwordToStore;
        if (rawPassword == null) {
            passwordToStore = null;
        } else if (rawPassword.startsWith("$2a$") || rawPassword.startsWith("$2y$") || rawPassword.startsWith("$2b$")) {
            passwordToStore = rawPassword;
        } else {
            passwordToStore = passwordEncryptionService.encryptPassword(rawPassword);
        }

        Client client = new Client(
                clientDto.id(),
                clientDto.name(),
                clientDto.email(),
                passwordToStore,
                clientDto.phone(),
                clientDto.cartId(),
                clientDto.role()
        );

        ClientEntity entity = ClientMapper.getInstance().fromClientToClientEntity(client);
        ClientEntity savedEntity = clientRepository.save(entity);
        Client savedClient = ClientMapper.getInstance().fromClientEntityToClient(savedEntity);
        return ClientMapper.getInstance().fromClientToClientDto(savedClient);
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
    public Optional<ClientDto> login(String email, String password) {
        return clientRepository.findByEmail(email)
                .filter(entity -> passwordEncryptionService.matches(password, entity.password()))
                .map(ClientMapper.getInstance()::fromClientEntityToClient)
                .map(ClientMapper.getInstance()::fromClientToClientDto);
    }
}
