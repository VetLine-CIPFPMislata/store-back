package org.example.storeback.persistence.dao;

import org.example.storeback.domain.repository.entity.SessionEntity;

import java.util.Optional;

public interface SessionJpaDao {
    Optional<SessionEntity> findByToken(String token);
    SessionEntity save(SessionEntity sessionEntity);
    void deleteByToken(String token);
}
