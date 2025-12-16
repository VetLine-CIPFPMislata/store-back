package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity;
import org.example.storeback.testutil.ProductFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductMapperPersistenceTest {

    @Test
    void jpa_to_product_entity() {
        ProductJpaEntity jpa = ProductFixtures.sampleProductJpaEntity();
        ProductEntity entity = ProductMapperPersistence.getInstance().fromProductJpaEntityToProductEntity(jpa);
        assertNotNull(entity);
        assertEquals(jpa.getId(), entity.id());
        assertEquals(jpa.getName(), entity.name());
        assertEquals(jpa.getProductDescription(), entity.productDescription());
        assertEquals(jpa.getBasePrice(), entity.basePrice());
        assertEquals(jpa.getDiscountPercentage(), entity.discountPercentage());
        assertEquals(jpa.getPictureProduct(), entity.pictureProduct());
        assertEquals(jpa.getQuantity(), entity.quantity());
        assertEquals(jpa.getRating(), entity.rating());
    }
    @Test
    void product_entity_to_jpa() {
        ProductEntity entity = ProductFixtures.sampleProductEntity();
        ProductJpaEntity jpa = ProductMapperPersistence.getInstance().fromProductEntityToProductJpaEntity(entity);
        assertNotNull(jpa);
        assertEquals(entity.id(), jpa.getId());
        assertEquals(entity.name(), jpa.getName());
        assertEquals(entity.productDescription(), jpa.getProductDescription());
        assertEquals(entity.basePrice(), jpa.getBasePrice());
        assertEquals(entity.discountPercentage(), jpa.getDiscountPercentage());
        assertEquals(entity.pictureProduct(), jpa.getPictureProduct());
        assertEquals(entity.quantity(), jpa.getQuantity());
        assertEquals(entity.rating(), jpa.getRating());
    }
}

