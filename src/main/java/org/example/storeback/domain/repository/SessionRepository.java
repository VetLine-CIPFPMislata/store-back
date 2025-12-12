package org.example.storeback.domain.repository;

import org.example.storeback.domain.repository.entity.SessionEntity;

import java.util.Optional;

public interface SessionRepository {
    Optional<SessionEntity> findByToken(String token);
    SessionEntity save(SessionEntity sessionEntity);
    void deleteByToken(String token);
}

