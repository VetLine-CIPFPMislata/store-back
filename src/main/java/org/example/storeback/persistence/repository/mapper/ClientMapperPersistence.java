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

    public ClientEntity fromClientJpaEntityToClientEntity(ClientJpaEntity clientJpaEntity) {
        if (clientJpaEntity == null) {
            return null;
        }
        return new ClientEntity(
                clientJpaEntity.getId(),
                clientJpaEntity.getName(),
                clientJpaEntity.getEmail(),
                clientJpaEntity.getPassword(),
                clientJpaEntity.getPhone(),
                clientJpaEntity.getCartId(),
                clientJpaEntity.getRole()
        );
    }

    public  ClientJpaEntity fromClientEntityToClientJpaEntity(ClientEntity clientEntity) {
        if (clientEntity == null) {
            return  null;
        }
        return new ClientJpaEntity(
                clientEntity.id(),
                clientEntity.name(),
                clientEntity.email(),
                clientEntity.password(),
                clientEntity.phone(),
                clientEntity.cartId(),
                clientEntity.role()
        );
    }

}
