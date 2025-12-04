package org.example.storeback.domain.mappers;

import org.example.storeback.domain.models.Client;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.domain.service.dto.ClientDto;

public class ClientMapper {
    private static ClientMapper instance;

    private ClientMapper() {
    }

    public static ClientMapper getInstance() {
        if (instance == null) {
            instance = new ClientMapper();
        }
        return instance;
    }

    public Client fromClientEntityToClient( ClientEntity clientEntity ){
        if( clientEntity == null ){
            throw new IllegalArgumentException("ClientEntity cannot be null");
        }
        return new Client(
                clientEntity.id(),
                clientEntity.name(),
                clientEntity.email(),
                clientEntity.password(),
                clientEntity.phone(),
                clientEntity.cartId(),
                clientEntity.role()
        );
    }

    public ClientEntity fromClientToClientEntity( Client client ){
        if( client == null ){
            throw new IllegalArgumentException("Client cannot be null");
        }
        return new ClientEntity(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPassword(),
                client.getPhone(),
                client.getCartId(),
                client.getRole()
        );
    }

    public Client fromClientDtoToClient( ClientDto clientDto ){
        if( clientDto == null ){
            throw new IllegalArgumentException("ClientDto cannot be null");
        }
        return new Client(
                clientDto.id(),
                clientDto.name(),
                clientDto.email(),
                clientDto.password(),
                clientDto.phone(),
                clientDto.cartId(),
                clientDto.role()
        );
    }

    public ClientDto fromClientToClientDto( Client client ){
        if( client == null ){
            throw new IllegalArgumentException("Client cannot be null");
        }
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPassword(),
                client.getPhone(),
                client.getCartId(),
                client.getRole()
        );
    }
}
