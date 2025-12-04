package org.example.storeback.persistence.repository;

import org.example.storeback.domain.repository.ClientRepository;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.persistence.dao.ClientJpaDao;

import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {

    private final ClientJpaDao clientJpaDao;

    public ClientRepositoryImpl(ClientJpaDao clientJpaDao) {
        this.clientJpaDao = clientJpaDao;
    }

    @Override
    public Optional<ClientEntity> findById(Long id) {
        return clientJpaDao.findById(id);
    }

    @Override
    public Optional<ClientEntity> findByEmail(String email) {
        return clientJpaDao.findByEmail(email);
    }

    @Override
    public ClientEntity save(ClientEntity clientEntity) {
        return clientJpaDao.save(clientEntity);
    }

    @Override
    public void deleteById(Long id) {
        clientJpaDao.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientJpaDao.existsByEmail(email);
    }
}
