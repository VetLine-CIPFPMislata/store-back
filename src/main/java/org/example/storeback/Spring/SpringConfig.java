package org.example.storeback.Spring;

import org.example.storeback.domain.repository.CategoryRepository;
import org.example.storeback.domain.service.CategoryService;
import org.example.storeback.domain.service.impl.CategoryServiceImpl;
import org.example.storeback.persistence.dao.CategoryJpaDao;
import org.example.storeback.persistence.dao.jpa.impl.CategoryJpaDaoImpl;
import org.example.storeback.persistence.repository.CategoryRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public CategoryRepository categoryRepository(CategoryJpaDao categoryJpaDao){return new CategoryRepositoryImpl(categoryJpaDao);}
    @Bean
    public CategoryService categoryService(CategoryRepository categoryRepository){return new CategoryServiceImpl(categoryRepository);}
    @Bean
    public CategoryJpaDao categoryJpaDao(){return new CategoryJpaDaoImpl();}
}
