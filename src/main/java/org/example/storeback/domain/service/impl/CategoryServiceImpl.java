package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.exception.BusinessException;
import org.example.storeback.domain.mappers.CategoryMapper;
import org.example.storeback.domain.repository.CategoryRepository;
import org.example.storeback.domain.repository.ProductRepository;
import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.domain.service.CategoryService;
import org.example.storeback.domain.service.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper.getInstance()::fromCategoryEntityToCategory)
                .map(CategoryMapper.getInstance()::fromCategoryToCategoryDto)
                .toList();
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper.getInstance()::fromCategoryEntityToCategory)
                .map(CategoryMapper.getInstance()::fromCategoryToCategoryDto);
    }

    @Override
    public Optional<CategoryDto> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(CategoryMapper.getInstance()::fromCategoryEntityToCategory)
                .map(CategoryMapper.getInstance()::fromCategoryToCategoryDto);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        if (categoryDto == null) {
            throw new IllegalArgumentException("CategoryDto cannot be null");
        }

        CategoryMapper mapper = CategoryMapper.getInstance();

        var domain = mapper.fromCategoryDtoToCategory(categoryDto);
        CategoryEntity entityToSave = mapper.fromCategoryToCategoryEntity(domain);

        if (categoryDto.id() == null) {
            CategoryEntity savedEntity = categoryRepository.save(entityToSave);
            return mapper.fromCategoryToCategoryDto(
                    mapper.fromCategoryEntityToCategory(savedEntity)
            );
        } else {
            Long id = categoryDto.id();
            if (categoryRepository.findById(id).isEmpty()) {
                throw new IllegalArgumentException("Category with id " + id + " not found");
            }

            CategoryEntity savedEntity = categoryRepository.save(entityToSave);
            return mapper.fromCategoryToCategoryDto(
                    mapper.fromCategoryEntityToCategory(savedEntity)
            );
        }
    }

    @Override
    public void deleteById(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Category with id " + id + " not found");
        }

        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if (categoryEntity.isPresent()) {
            String categoryName = categoryEntity.get().name();
            List<?> productsInCategory = productRepository.findByCategory(categoryName);

            if (!productsInCategory.isEmpty()) {
                throw new BusinessException("Cannot delete category with id " + id + " because it has " +
                        productsInCategory.size() + " product(s) assigned to it");
            }
        }

        categoryRepository.deleteById(id);
    }
}
