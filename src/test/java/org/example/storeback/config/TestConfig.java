package org.example.storeback.config;

import org.example.storeback.persistence.dao.CategoryJpaDao;
import org.example.storeback.persistence.dao.jpa.impl.CategoryJpaDaoImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public CategoryJpaDao categoryJpaDao() {
        return new CategoryJpaDaoImpl();
    }
}

