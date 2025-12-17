package org.example.storeback.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.storeback.config.TestConfig;
import org.example.storeback.persistence.dao.ProductJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
public class ProductJpaDaoImplTest {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private ProductJpaDao productJpaDao;

    @Test
    @DisplayName("Test insert method persists ProductJpaEntity")
    void testInsert() {
        CategoryJpaEntity category = new CategoryJpaEntity();
        category.setName("TestCategory");
        category.setDescription("Desc cat");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity product = new ProductJpaEntity();
        product.setCategory(category);
        product.setName("Product 1");
        product.setProductDescription("Desc");
        product.setBasePrice(new BigDecimal("10.00"));
        product.setDiscountPercentage(new BigDecimal("5.00"));
        product.setPictureProduct("http://img");
        product.setQuantity(5);
        product.setRating(4);

        String sql= "SELECT COUNT(p) FROM ProductJpaEntity p";
        long countbefore=entityManager.createQuery(sql,Long.class).getSingleResult();

        ProductJpaEntity saved = productJpaDao.save(product);
        entityManager.flush();


        long countafter=entityManager.createQuery(sql,Long.class).getSingleResult();

        long lastId = entityManager.createQuery("SELECT MAX(p.id) FROM ProductJpaEntity p", Long.class)
                .getSingleResult();

        assertAll(
                () -> assertNotNull(saved),
                () -> assertEquals(lastId, saved.getId()),
                () -> assertEquals(product.getId(), saved.getId()),
                () -> assertEquals(product.getName(), saved.getName()),
                () -> assertEquals(product.getProductDescription(), saved.getProductDescription()),
                () -> assertEquals(product.getBasePrice(), saved.getBasePrice()),
                () -> assertEquals(product.getDiscountPercentage(), saved.getDiscountPercentage()),
                () -> assertEquals(product.getPictureProduct(), saved.getPictureProduct()),
                () -> assertEquals(product.getQuantity(), saved.getQuantity()),
                () -> assertEquals(product.getRating(), saved.getRating())

        );
    }

    @Test
    @DisplayName("Test update method updates ProductJpaEntity")
    void testUpdate() {
        CategoryJpaEntity category = new CategoryJpaEntity();
        category.setName("TestCategory");
        category.setDescription("Desc cat");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity product = new ProductJpaEntity();
        product.setCategory(category);
        product.setName("Product 1");
        product.setProductDescription("Desc");
        product.setBasePrice(new BigDecimal("10.00"));
        product.setDiscountPercentage(new BigDecimal("5.00"));
        product.setPictureProduct("http://img");
        product.setQuantity(5);
        product.setRating(4);

        ProductJpaEntity saved = productJpaDao.save(product);
        entityManager.flush();

        ProductJpaEntity productToUpdate= new ProductJpaEntity(
        saved.getId(),
        saved.getCategory(),
        "Product updated ",
                "Description",
                new BigDecimal("18.00"),
                new BigDecimal("15.00"),
                "http://img/gheudes",
                4,
                5
        );
        ProductJpaEntity updated = productJpaDao.save(productToUpdate);

        assertNotNull(updated);
        assertEquals(productToUpdate.getId(), updated.getId());
        assertEquals(productToUpdate.getName(), updated.getName());
        assertEquals(productToUpdate.getProductDescription(), updated.getProductDescription());
        assertEquals(productToUpdate.getBasePrice(), updated.getBasePrice());
        assertEquals(productToUpdate.getDiscountPercentage(), updated.getDiscountPercentage());
        assertEquals(productToUpdate.getPictureProduct(), updated.getPictureProduct());
        assertEquals(productToUpdate.getQuantity(), updated.getQuantity());
        assertEquals(productToUpdate.getRating(), updated.getRating());
    }
    @Test
    void testProductFindById(){
        CategoryJpaEntity category = new CategoryJpaEntity();
        category.setName("TestCategory");
        category.setDescription("Desc cat");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity product = new ProductJpaEntity();
        product.setCategory(category);
        product.setName("Product 1");
        product.setProductDescription("Desc");
        product.setBasePrice(new BigDecimal("10.00"));
        product.setDiscountPercentage(new BigDecimal("5.00"));
        product.setPictureProduct("http://img");
        product.setQuantity(5);
        product.setRating(4);

        ProductJpaEntity saved = productJpaDao.save(product);
        entityManager.flush();

        ProductJpaEntity found = productJpaDao.findById(saved.getId()).orElseThrow();

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals(saved.getName(), found.getName());
        assertEquals(saved.getProductDescription(), found.getProductDescription());
        assertTrue(saved.getBasePrice().compareTo(found.getBasePrice()) == 0);
        assertTrue(saved.getDiscountPercentage().compareTo(found.getDiscountPercentage()) == 0);
        assertEquals(saved.getPictureProduct(), found.getPictureProduct());
        assertEquals(saved.getQuantity(), found.getQuantity());
        assertEquals(saved.getRating(), found.getRating());
    }
    @Test
    void testProductDeleteById(){
        CategoryJpaEntity category = new CategoryJpaEntity();
        category.setName("TestCategory");
        category.setDescription("Desc cat");
        entityManager.persist(category);
        entityManager.flush();

        ProductJpaEntity product = new ProductJpaEntity();
        product.setCategory(category);
        product.setName("Product 1");
        product.setProductDescription("Desc");
        product.setBasePrice(new BigDecimal("10.00"));
        product.setDiscountPercentage(new BigDecimal("5.00"));
        product.setPictureProduct("http://img");
        product.setQuantity(5);
        product.setRating(4);
        ProductJpaEntity saved = productJpaDao.save(product);
        entityManager.flush();

        productJpaDao.deleteById(saved.getId());
        assertFalse(productJpaDao.findById(saved.getId()).isPresent());
    }



}
