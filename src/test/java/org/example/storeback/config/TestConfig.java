package org.example.storeback.config;

import org.example.storeback.persistence.dao.CategoryJpaDao;
import org.example.storeback.persistence.dao.ClientJpaDao;
import org.example.storeback.persistence.dao.ProductJpaDao;
import org.example.storeback.persistence.dao.jpa.impl.CategoryJpaDaoImpl;
import org.example.storeback.persistence.dao.jpa.impl.ClientJpaDaoImpl;
import org.example.storeback.persistence.dao.jpa.impl.ProductJpaDaoImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@EnableJpaRepositories(basePackages = "org.example.storeback.persistence.dao.jpa")
@EntityScan(basePackages = "org.example.storeback.persistence.dao.jpa.entity")
@EnableAutoConfiguration
public class TestConfig {

    @Bean
    public CategoryJpaDao categoryJpaDao() {
        return new CategoryJpaDaoImpl();
    }

    @Bean
    public ClientJpaDao clientJpaDao() {
        return new ClientJpaDaoImpl();
    }

    @Bean
    public ProductJpaDao productJpaDao() {
        return new ProductJpaDaoImpl();
    }
}
