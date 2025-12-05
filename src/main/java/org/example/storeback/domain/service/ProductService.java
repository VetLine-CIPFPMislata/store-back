package org.example.storeback.domain.service;

import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.models.Product;
import org.example.storeback.domain.service.dto.ProductDto;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<ProductDto> findAll(int page, int size);
    Optional<ProductDto> findById(Long id);
    Optional<ProductDto> findByName(String name);
    Optional<ProductDto> findByCategory(String category);
    List<ProductDto> findByRating(int min, int max);
    ProductDto save(ProductDto productDto);
    void deleteById(Long id);
}
