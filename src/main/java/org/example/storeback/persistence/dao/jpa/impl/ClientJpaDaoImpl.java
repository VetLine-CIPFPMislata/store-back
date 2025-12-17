package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.persistence.dao.ClientJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.ClientJpaEntity;
import org.example.storeback.persistence.repository.mapper.ClientMapperPersistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public class ClientJpaDaoImpl implements ClientJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ClientEntity> findAll() {
        TypedQuery<ClientJpaEntity> query = entityManager.createQuery(
                "SELECT c FROM ClientJpaEntity c",
                ClientJpaEntity.class
        );
        return query.getResultList().stream()
                .map(ClientMapperPersistence.getInstance()::fromClientJpaEntityToClientEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClientEntity> findById(Long id) {
        ClientJpaEntity jpaEntity = entityManager.find(ClientJpaEntity.class, id);
        return Optional.ofNullable(jpaEntity)
                .map(ClientMapperPersistence.getInstance()::fromClientJpaEntityToClientEntity);
    }

    @Override
    public Optional<ClientEntity> findByEmail(String email) {
        TypedQuery<ClientJpaEntity> query = entityManager.createQuery(
                "SELECT c FROM ClientJpaEntity c WHERE c.email = :email",
                ClientJpaEntity.class
        );
        query.setParameter("email", email);

        return query.getResultStream()
                .findFirst()
                .map(ClientMapperPersistence.getInstance()::fromClientJpaEntityToClientEntity);
    }

    @Override
    public ClientEntity save(ClientEntity clientEntity) {
        ClientJpaEntity jpaEntity = ClientMapperPersistence.getInstance()
                .fromClientEntityToClientJpaEntity(clientEntity);

        if (jpaEntity.getId() == null) {
            entityManager.persist(jpaEntity);
        } else {
            jpaEntity = entityManager.merge(jpaEntity);
        }

        return ClientMapperPersistence.getInstance()
                .fromClientJpaEntityToClientEntity(jpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        ClientJpaEntity entity = entityManager.find(ClientJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(c) FROM ClientJpaEntity c WHERE c.email = :email",
                Long.class
        );
        query.setParameter("email", email);
        Long count = query.getSingleResult();
        return count > 0;
    }
}
