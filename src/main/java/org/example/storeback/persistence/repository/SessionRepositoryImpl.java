package org.example.storeback.persistence.repository;

import org.example.storeback.domain.repository.SessionRepository;
import org.example.storeback.domain.repository.entity.SessionEntity;
import org.example.storeback.persistence.dao.SessionJpaDao;

import java.util.Optional;

public class SessionRepositoryImpl implements SessionRepository {

    private final SessionJpaDao sessionJpaDao;

    public SessionRepositoryImpl(SessionJpaDao sessionJpaDao) {
        this.sessionJpaDao = sessionJpaDao;
    }

    @Override
    public Optional<SessionEntity> findByToken(String token) {
        return sessionJpaDao.findByToken(token);
    }

    @Override
    public SessionEntity save(SessionEntity sessionEntity) {
        return sessionJpaDao.save(sessionEntity);
    }

    @Override
    public void deleteByToken(String token) {
        sessionJpaDao.deleteByToken(token);
    }
}

