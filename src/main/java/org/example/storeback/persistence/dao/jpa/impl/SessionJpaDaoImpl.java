package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.example.storeback.domain.repository.entity.SessionEntity;
import org.example.storeback.persistence.dao.SessionJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.SessionJpaEntity;
import org.example.storeback.persistence.repository.mapper.SessionMapperPersistence;

import java.util.Optional;

@Transactional
public class SessionJpaDaoImpl implements SessionJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SessionEntity> findByToken(String token) {
        TypedQuery<SessionJpaEntity> query = entityManager.createQuery(
                "SELECT s FROM SessionJpaEntity s WHERE s.token = :token",
                SessionJpaEntity.class
        );
        query.setParameter("token", token);

        return query.getResultStream()
                .findFirst()
                .map(SessionMapperPersistence.getInstance()::fromSessionJpaEntityToSessionEntity);
    }

    @Override
    public SessionEntity save(SessionEntity sessionEntity) {
        SessionJpaEntity jpaEntity = SessionMapperPersistence.getInstance()
                .fromSessionEntityToSessionJpaEntity(sessionEntity);

        if (jpaEntity.getId() == null) {
            entityManager.persist(jpaEntity);
        } else {
            jpaEntity = entityManager.merge(jpaEntity);
        }

        return SessionMapperPersistence.getInstance()
                .fromSessionJpaEntityToSessionEntity(jpaEntity);
    }

    @Override
    public void deleteByToken(String token) {
        TypedQuery<SessionJpaEntity> query = entityManager.createQuery(
                "SELECT s FROM SessionJpaEntity s WHERE s.token = :token",
                SessionJpaEntity.class
        );
        query.setParameter("token", token);
        
        query.getResultStream().findFirst().ifPresent(entityManager::remove);
    }
}
