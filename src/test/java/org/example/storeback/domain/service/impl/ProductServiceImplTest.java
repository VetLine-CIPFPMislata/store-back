package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.models.Category;
import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.repository.ProductRepository;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.domain.service.dto.ProductDto;
import org.example.storeback.testutil.ProductFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository repository;
    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    void save_converts_and_returns_dto() {
        ProductDto dto = ProductFixtures.sampleProductDto();
        var entity = ProductFixtures.sampleProductEntity();
        when(repository.save(any())).thenReturn(entity);

        var result = productService.save(dto);
        assertNotNull(result);
        assertEquals(dto.name(), result.name());
        verify(repository).save(any());
    }

    @Test
    void productFindById() {
        var entity = ProductFixtures.sampleProductEntity();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var opt = productService.findById(1L);
        assertTrue(opt.isPresent());
        assertEquals(entity.id(), opt.get().id());
    }

    @Test
    void findAll_invalidPage_throws() {
        assertThrows(IllegalArgumentException.class, () -> productService.findAll(0, 10));
        assertThrows(IllegalArgumentException.class, () -> productService.findAll(1, 0));
    }

    @Test
    void returns_empty_when_getAll_finds_no_products(){

        when(repository.findAll(1,10)).thenReturn(new Page<>(List.of(),1,10,0));
        Page<ProductDto> result= productService.findAll(1,10);
        assertThat(result.data()).isEmpty();
        assertThat(result.totalElements()).isZero();
        assertThat(result.totalPages()).isZero();
    }

}


