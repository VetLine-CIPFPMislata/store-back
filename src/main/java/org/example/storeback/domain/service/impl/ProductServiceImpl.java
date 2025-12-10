package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.mappers.ProductMapper;
import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.repository.ProductRepository;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.domain.service.ProductService;
import org.example.storeback.domain.service.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> findAll(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        Page<ProductEntity> productEntityPage = productRepository.findAll(page, size);
        List<ProductDto> itemsDto = productEntityPage.data()
                .stream()
                .map(ProductMapper.getInstance()::fromProductEntityToProduct)
                .map(ProductMapper.getInstance()::fromProductToProductDto)
                .toList();
        return new Page<>(
                itemsDto,
                productEntityPage.pageNumber(),
                productEntityPage.pageSize(),
                productEntityPage.totalElements()
        );
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper.getInstance()::fromProductEntityToProduct)
                .map(ProductMapper.getInstance()::fromProductToProductDto);
    }

    @Override
    public Optional<ProductDto> findByName(String name) {
        return productRepository.findByName(name)
                .map(ProductMapper.getInstance()::fromProductEntityToProduct)
                .map(ProductMapper.getInstance()::fromProductToProductDto);
    }

    @Override
    public List<ProductDto> findByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(ProductMapper.getInstance()::fromProductEntityToProduct)
                .map(ProductMapper.getInstance()::fromProductToProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> findByRating(int min, int max) {
        return productRepository.findByRating(min, max)
                .stream()
                .map(ProductMapper.getInstance()::fromProductEntityToProduct)
                .map(ProductMapper.getInstance()::fromProductToProductDto)
                .toList();
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        ProductEntity entityToSave = ProductMapper.getInstance()
                .fromProductToProductEntity(
                        ProductMapper.getInstance().fromProductDtoToProduct(productDto)
                );

        ProductEntity savedEntity = productRepository.save(entityToSave);
        return ProductMapper.getInstance()
                .fromProductToProductDto(
                        ProductMapper.getInstance().fromProductEntityToProduct(savedEntity)
                );
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}