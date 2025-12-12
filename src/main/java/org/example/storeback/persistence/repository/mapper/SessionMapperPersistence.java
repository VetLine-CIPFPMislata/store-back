package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.SessionEntity;
import org.example.storeback.persistence.dao.jpa.entity.SessionJpaEntity;

public class SessionMapperPersistence {
    private static SessionMapperPersistence INSTANCE;

    public static SessionMapperPersistence getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SessionMapperPersistence();
        }
        return INSTANCE;
    }

    public SessionMapperPersistence() {
    }

    public SessionEntity fromSessionJpaEntityToSessionEntity(SessionJpaEntity sessionJpaEntity) {
        if (sessionJpaEntity == null) {
            return null;
        }
        return new SessionEntity(
                sessionJpaEntity.getId(),
                sessionJpaEntity.getClientId(),
                sessionJpaEntity.getToken(),
                sessionJpaEntity.getLoginDate()
        );
    }

    public SessionJpaEntity fromSessionEntityToSessionJpaEntity(SessionEntity sessionEntity) {
        if (sessionEntity == null) {
            return null;
        }
        return new SessionJpaEntity(
                sessionEntity.id(),
                sessionEntity.clientId(),
                sessionEntity.token(),
                sessionEntity.loginDate()
        );
    }
}

