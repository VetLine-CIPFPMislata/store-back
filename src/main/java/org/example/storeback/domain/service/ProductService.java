package org.example.storeback.domain.service;

import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.models.Product;
import org.example.storeback.domain.service.dto.ProductDto;

import java.awt.print.Pageable;

public interface ProductService {
    Page<ProductDto> findAllProducts(int page, int size);
    ProductDto findProductById(Long id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto);
    void deleteProduct(Long id);
}
