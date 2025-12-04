package org.example.storeback.persistence.repository.mapper;


import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.persistence.dao.jpa.entity.ClientJpaEntity;

public class ClientMapperPersistence {
    private static  ClientMapperPersistence INSTANCE;

    public static ClientMapperPersistence getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientMapperPersistence();
        }
        return  INSTANCE;
    }
    public  ClientMapperPersistence() {
    }

    public ClientEntity fromClientJpaEntityToClientEntity(ClientEntity clientEntity) {
        if (clientEntity == null) {
            return null;
        }
        return new ClientEntity(
                clientEntity.id(),
                clientEntity.name(),
                clientEntity.email(),
                clientEntity.password(),
                clientEntity.phone(),
                clientEntity.cartId(),
                clientEntity.role()
        );
    }

    public  ClientEntity fromClientEntityToClientJpaEntity(ClientJpaEntity clientEntity) {
        if (clientEntity == null) {
            return  null;
        }
        return new ClientEntity(
                clientEntity.getId(),
                clientEntity.getName(),
                clientEntity.getEmail(),
                clientEntity.getPassword(),
                clientEntity.getPhone(),
                clientEntity.getCartId(),
                clientEntity.getRole()
        );
    }

}
