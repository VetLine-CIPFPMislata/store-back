package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.persistence.dao.ClientJpaDao;

import java.util.Optional;

@Transactional
public class ClientJpaDaoImpl implements ClientJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ClientEntity> findById(Long id) {
        String jpql="SELECT c FROM ClientJpaEntity c WHERE c.id = :id";
        return entityManager.createQuery(jpql,ClientEntity.class)
                .setParameter("id",id)
                .getResultStream().findFirst();
    }

    @Override
    public Optional<ClientEntity> findByEmail(String email) {
        String jpql="SELECT c FROM ClientJpaEntity c WHERE c.email = :email";
        return entityManager.createQuery(jpql,ClientEntity.class)
                .setParameter("email",email)
                .getResultStream().findFirst();
    }

    @Override
    public ClientEntity save(ClientEntity clientEntity) {
        if (clientEntity.id() == null) {
            entityManager.persist(clientEntity);
            return clientEntity;
        }
        return entityManager.merge(clientEntity);
    }

    public void deleteById(Long id) {
        String jpql = "DELETE FROM ClientJpaEntity c WHERE c.id = :id";
        entityManager.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();
    }

        @Override
    public boolean existsByEmail(String email) {
        String jpql="SELECT COUNT(c) FROM ClientJpaEntity c WHERE c.email = :email";
           Long count= entityManager.createQuery(jpql,Long.class)
                .setParameter("email",email)
                .getSingleResult();
           if (count==0){
               return false;
           }else{
               return true;
           }
    }
}
