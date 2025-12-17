package org.example.storeback.persistence.repository;

import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.persistence.dao.ProductJpaDao;
import org.example.storeback.persistence.dao.jpa.entity.ProductJpaEntity;
import org.example.storeback.testutil.ProductFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductRepositoryImpl Tests")
class ProductRepositoryImplTest {

    @Mock
    private ProductJpaDao productJpaDao;

    private ProductRepositoryImpl productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryImpl(productJpaDao);
    }

    @Test
    @DisplayName("findAll - Debe delegar al DAO y retornar página de productos")
    void findAll_ShouldDelegateToDao() {
        ProductJpaEntity jpa1 = ProductFixtures.sampleProductJpaEntity();
        ProductJpaEntity jpa2 = ProductFixtures.sampleProductJpaEntity();
        List<ProductJpaEntity> jpaList = Arrays.asList(jpa1, jpa2);
        Page<ProductJpaEntity> jpaPage = new Page<>(jpaList, 1, 10, 2L);

        when(productJpaDao.findAll(1, 10)).thenReturn(jpaPage);

        Page<ProductEntity> result = productRepository.findAll(1, 10);

        assertNotNull(result);
        assertEquals(2, result.data().size());
        assertEquals(1, result.pageNumber());
        assertEquals(10, result.pageSize());
        assertEquals(2L, result.totalElements());
        verify(productJpaDao).findAll(1, 10);
    }

    @Test
    @DisplayName("findById - Debe delegar al DAO y retornar producto cuando existe")
    void findById_ShouldDelegateToDao_WhenProductExists() {
        Long productId = 1L;
        ProductJpaEntity jpa = ProductFixtures.sampleProductJpaEntity();
        when(productJpaDao.findById(productId)).thenReturn(Optional.of(jpa));

        Optional<ProductEntity> result = productRepository.findById(productId);

        assertTrue(result.isPresent());
        assertEquals(jpa.getId(), result.get().id());
        assertEquals(jpa.getName(), result.get().name());
        verify(productJpaDao, times(1)).findById(productId);
    }

    @Test
    @DisplayName("findById - Debe retornar Optional vacío cuando no existe")
    void findById_ShouldReturnEmpty_WhenProductDoesNotExist() {
        Long productId = 999L;
        when(productJpaDao.findById(productId)).thenReturn(Optional.empty());

        Optional<ProductEntity> result = productRepository.findById(productId);

        assertFalse(result.isPresent());
        verify(productJpaDao).findById(productId);
    }

    @Test
    @DisplayName("findByName - Debe delegar al DAO y retornar producto por nombre")
    void findByName_ShouldDelegateToDao_WhenProductExists() {
        String productName = "Product 1";
        ProductJpaEntity jpa = ProductFixtures.sampleProductJpaEntity();
        when(productJpaDao.findByName(productName)).thenReturn(Optional.of(jpa));

        Optional<ProductEntity> result = productRepository.findByName(productName);

        assertTrue(result.isPresent());
        assertEquals(jpa.getName(), result.get().name());
        verify(productJpaDao, times(1)).findByName(productName);
    }

    @Test
    @DisplayName("findByName - Debe retornar Optional vacío cuando no existe")
    void findByName_ShouldReturnEmpty_WhenProductDoesNotExist() {
        String productName = "Nonexistent Product";
        when(productJpaDao.findByName(productName)).thenReturn(Optional.empty());

        Optional<ProductEntity> result = productRepository.findByName(productName);

        assertFalse(result.isPresent());
        verify(productJpaDao).findByName(productName);
    }

    @Test
    @DisplayName("findByCategory - Debe delegar al DAO y retornar productos por categoría")
    void findByCategory_ShouldDelegateToDao() {
        String categoryName = "Cat1";
        ProductJpaEntity jpa1 = ProductFixtures.sampleProductJpaEntity();
        ProductJpaEntity jpa2 = ProductFixtures.sampleProductJpaEntity();
        List<ProductJpaEntity> jpaList = Arrays.asList(jpa1, jpa2);
        when(productJpaDao.findByCategory(categoryName)).thenReturn(jpaList);

        List<ProductEntity> result = productRepository.findByCategory(categoryName);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productJpaDao).findByCategory(categoryName);
    }

    @Test
    @DisplayName("findByCategory - Debe retornar lista vacía cuando no hay productos")
    void findByCategory_ShouldReturnEmptyList_WhenNoProductsFound() {
        String categoryName = "NonexistentCategory";
        when(productJpaDao.findByCategory(categoryName)).thenReturn(List.of());

        List<ProductEntity> result = productRepository.findByCategory(categoryName);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productJpaDao).findByCategory(categoryName);
    }

    @Test
    @DisplayName("findByRating - Debe delegar al DAO y retornar productos por rango de rating")
    void findByRating_ShouldDelegateToDao() {
        int minRating = 3;
        int maxRating = 5;
        ProductEntity entity1 = ProductFixtures.sampleProductEntity();
        ProductEntity entity2 = ProductFixtures.sampleProductEntity();
        List<ProductEntity> entities = Arrays.asList(entity1, entity2);
        when(productJpaDao.findByRating(minRating, maxRating)).thenReturn(entities);

        List<ProductEntity> result = productRepository.findByRating(minRating, maxRating);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productJpaDao).findByRating(minRating, maxRating);
    }

    @Test
    @DisplayName("save - Debe delegar al DAO para guardar nuevo producto")
    void save_ShouldDelegateToDao_WhenSavingNewProduct() {
        ProductEntity newEntity = ProductFixtures.sampleProductEntity();
        ProductJpaEntity jpa = ProductFixtures.sampleProductJpaEntity();
        when(productJpaDao.save(any(ProductJpaEntity.class))).thenReturn(jpa);

        ProductEntity result = productRepository.save(newEntity);

        assertNotNull(result);
        assertEquals(jpa.getId(), result.id());
        assertEquals(jpa.getName(), result.name());
        verify(productJpaDao).save(any(ProductJpaEntity.class));
    }

    @Test
    @DisplayName("save - Debe delegar al DAO para actualizar producto existente")
    void save_ShouldDelegateToDao_WhenUpdatingProduct() {
        ProductEntity existingEntity = ProductFixtures.sampleProductEntity();
        ProductJpaEntity jpa = ProductFixtures.sampleProductJpaEntity();
        when(productJpaDao.save(any(ProductJpaEntity.class))).thenReturn(jpa);

        ProductEntity result = productRepository.save(existingEntity);

        assertNotNull(result);
        assertEquals(jpa.getId(), result.id());
        verify(productJpaDao).save(any(ProductJpaEntity.class));
    }

    @Test
    @DisplayName("deleteById - Debe delegar al DAO para eliminar producto")
    void deleteById_ShouldDelegateToDao() {
        Long productId = 1L;
        doNothing().when(productJpaDao).deleteById(productId);

        productRepository.deleteById(productId);

        verify(productJpaDao).deleteById(productId);
    }
}


