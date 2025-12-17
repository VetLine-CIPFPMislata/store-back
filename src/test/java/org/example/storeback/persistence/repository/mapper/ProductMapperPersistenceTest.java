package org.example.storeback.persistence.repository.mapper;

import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity;
import org.example.storeback.testutil.ProductFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductMapperPersistence Tests")
public class ProductMapperPersistenceTest {

    private final ProductMapperPersistence mapper = ProductMapperPersistence.getInstance();

    @Test
    @DisplayName("fromProductJpaEntityToProductEntity - Debe mapear correctamente de JPA a Entity")
    void jpa_to_product_entity() {
        ProductJpaEntity jpa = ProductFixtures.sampleProductJpaEntity();

        ProductEntity entity = mapper.fromProductJpaEntityToProductEntity(jpa);

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
    @DisplayName("fromProductEntityToProductJpaEntity - Debe mapear correctamente de Entity a JPA")
    void product_entity_to_jpa() {
        ProductEntity entity = ProductFixtures.sampleProductEntity();

        ProductJpaEntity jpa = mapper.fromProductEntityToProductJpaEntity(entity);

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

    @Test
    @DisplayName("fromProductJpaEntityToProductEntity - Debe retornar null cuando JPA es null")
    void jpa_to_product_entity_when_null() {
        ProductEntity entity = mapper.fromProductJpaEntityToProductEntity(null);

        assertNull(entity);
    }

    @Test
    @DisplayName("fromProductEntityToProductJpaEntity - Debe retornar null cuando Entity es null")
    void product_entity_to_jpa_when_null() {
        ProductJpaEntity jpa = mapper.fromProductEntityToProductJpaEntity(null);

        assertNull(jpa);
    }

    @Test
    @DisplayName("Mapper - Debe ser singleton")
    void mapper_is_singleton() {
        ProductMapperPersistence instance1 = ProductMapperPersistence.getInstance();
        ProductMapperPersistence instance2 = ProductMapperPersistence.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    @DisplayName("Mapeo bidireccional - Debe mantener consistencia de datos")
    void bidirectional_mapping_consistency() {
        ProductJpaEntity originalJpa = ProductFixtures.sampleProductJpaEntity();

        ProductEntity entity = mapper.fromProductJpaEntityToProductEntity(originalJpa);
        ProductJpaEntity mappedBackJpa = mapper.fromProductEntityToProductJpaEntity(entity);

        assertEquals(originalJpa.getId(), mappedBackJpa.getId());
        assertEquals(originalJpa.getName(), mappedBackJpa.getName());
        assertEquals(originalJpa.getProductDescription(), mappedBackJpa.getProductDescription());
        assertEquals(originalJpa.getBasePrice(), mappedBackJpa.getBasePrice());
        assertEquals(originalJpa.getDiscountPercentage(), mappedBackJpa.getDiscountPercentage());
        assertEquals(originalJpa.getPictureProduct(), mappedBackJpa.getPictureProduct());
        assertEquals(originalJpa.getQuantity(), mappedBackJpa.getQuantity());
        assertEquals(originalJpa.getRating(), mappedBackJpa.getRating());
    }

    @Test
    @DisplayName("fromProductJpaEntityToProductEntity - Debe mapear Category correctamente")
    void jpa_to_product_entity_with_category() {
        ProductJpaEntity jpa = ProductFixtures.sampleProductJpaEntity();

        ProductEntity entity = mapper.fromProductJpaEntityToProductEntity(jpa);

        assertNotNull(entity);
        assertNotNull(entity.category());
        assertEquals(jpa.getCategory().getId(), entity.category().getId());
        assertEquals(jpa.getCategory().getName(), entity.category().getName());
        assertEquals(jpa.getCategory().getDescription(), entity.category().getDescription());
    }

    @Test
    @DisplayName("fromProductEntityToProductJpaEntity - Debe mapear Category correctamente")
    void product_entity_to_jpa_with_category() {
        ProductEntity entity = ProductFixtures.sampleProductEntity();

        ProductJpaEntity jpa = mapper.fromProductEntityToProductJpaEntity(entity);

        assertNotNull(jpa);
        assertNotNull(jpa.getCategory());
        assertEquals(entity.category().getId(), jpa.getCategory().getId());
        assertEquals(entity.category().getName(), jpa.getCategory().getName());
        assertEquals(entity.category().getDescription(), jpa.getCategory().getDescription());
    }
}
