package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.mappers.ProductMapper;
import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.models.Product;
import org.example.storeback.domain.repository.ProductRepository;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.domain.service.ProductService;
import org.example.storeback.domain.service.dto.ProductDto;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> findAllProducts(int page, int size) {
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
    public ProductDto findProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper.getInstance()::fromProductEntityToProduct)
                .map(ProductMapper.getInstance()::fromProductToProductDto)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }

    @Override
    public ProductDto createProduct(ProductDto productdto) {
        if(productRepository.findById(productdto.id()).isPresent()) {
            throw new IllegalArgumentException("Product with id " + productdto.id() + " already exists");
        }

        // BookDto → Book → BookEntity
        ProductEntity entityToSave = ProductMapper.getInstance()
                .fromProductToProductEntity(
                        ProductMapper.getInstance().fromProductDtoToProduct(productdto)
                );

        ProductEntity savedEntity = productRepository.save(entityToSave);
        return ProductMapper.getInstance()
                .fromProductToProductDto(
                        ProductMapper.getInstance().fromProductEntityToProduct(savedEntity)
                );
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        ProductEntity existingEntity = productRepository.findById(productDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productDto.id() + " not found"));


        ProductEntity updatedEntity = ProductMapper.getInstance()
                .fromProductToProductEntity(
                        ProductMapper.getInstance().fromProductDtoToProduct(productDto)
                );

        // Mantener el ID de la entidad existente
        ProductEntity entityToSave = new ProductEntity(
                existingEntity.id(),
                updatedEntity.category(),
                updatedEntity.name(),
                updatedEntity.productDescription(),
                updatedEntity.basePrice(),
                updatedEntity.discountPercentage(),
                updatedEntity.pictureProduct(),
                updatedEntity.quantity(),
                updatedEntity.rating()
        );

        ProductEntity savedEntity = productRepository.save(entityToSave);
        return ProductMapper.getInstance()
                .fromProductToProductDto(
                        ProductMapper.getInstance().fromProductEntityToProduct(savedEntity)
                );

    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
