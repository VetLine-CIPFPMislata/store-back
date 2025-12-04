package org.example.storeback.domain.service;

import org.example.storeback.domain.models.Category;
import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.domain.service.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> findAll();
    Optional<CategoryDto> findById(Long id);
    Optional<CategoryDto> findByName(String name);
    CategoryDto save(CategoryDto categoryDto);
    void deleteById(Long id);
}
