package org.example.storeback.Spring;


import org.example.storeback.domain.repository.CategoryRepository;
import org.example.storeback.domain.repository.ClientRepository;
import org.example.storeback.domain.repository.ProductRepository;
import org.example.storeback.domain.repository.SessionRepository;
import org.example.storeback.domain.service.CategoryService;
import org.example.storeback.domain.service.AuthService;
import org.example.storeback.domain.service.ClientService;
import org.example.storeback.domain.service.ProductService;
import org.example.storeback.domain.service.impl.CategoryServiceImpl;
import org.example.storeback.domain.service.impl.AuthServiceImpl;
import org.example.storeback.domain.service.impl.ClientServiceImpl;
import org.example.storeback.domain.service.impl.ProductServiceImpl;
import org.example.storeback.persistence.dao.CategoryJpaDao;
import org.example.storeback.persistence.dao.ClientJpaDao;
import org.example.storeback.persistence.dao.ProductJpaDao;
import org.example.storeback.persistence.dao.SessionJpaDao;
import org.example.storeback.persistence.dao.jpa.impl.CategoryJpaDaoImpl;
import org.example.storeback.persistence.dao.jpa.impl.ClientJpaDaoImpl;
import org.example.storeback.persistence.dao.jpa.impl.ProductJpaDaoImpl;
import org.example.storeback.persistence.dao.jpa.impl.SessionJpaDaoImpl;
import org.example.storeback.persistence.repository.CategoryRepositoryImpl;
import org.example.storeback.persistence.repository.ClientRepositoryImpl;
import org.example.storeback.persistence.repository.ProductRepositoryImpl;
import org.example.storeback.persistence.repository.SessionRepositoryImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public ProductJpaDao productJpaDao() {
        return new ProductJpaDaoImpl();
    }

    @Bean
    public ProductRepository productRepository(ProductJpaDao productJpaDao) {
        return new ProductRepositoryImpl(productJpaDao);
    }

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductServiceImpl(productRepository);
    }


    @Bean
    public CategoryRepository categoryRepository(CategoryJpaDao categoryJpaDao){return new CategoryRepositoryImpl(categoryJpaDao);}
    @Bean
    public CategoryService categoryService(CategoryRepository categoryRepository){return new CategoryServiceImpl(categoryRepository);}
    @Bean
    public CategoryJpaDao categoryJpaDao(){return new CategoryJpaDaoImpl();}

    @Bean
    public ClientRepository clientRepository(ClientJpaDao clientJpaDao){return new ClientRepositoryImpl(clientJpaDao);}
    @Bean
    public ClientJpaDao clientJpaDao(){return new ClientJpaDaoImpl();}
    @Bean
    public ClientService clientService(ClientRepository clientRepository){return new ClientServiceImpl(clientRepository);}

    @Bean
    public SessionJpaDao sessionJpaDao() {
        return new SessionJpaDaoImpl();
    }

    @Bean
    public SessionRepository sessionRepository(SessionJpaDao sessionJpaDao) {
        return new SessionRepositoryImpl(sessionJpaDao);
    }

    @Bean
    public AuthService authService(SessionRepository sessionRepository, ClientRepository clientRepository) {
        return new AuthServiceImpl(sessionRepository, clientRepository);
    }
}
