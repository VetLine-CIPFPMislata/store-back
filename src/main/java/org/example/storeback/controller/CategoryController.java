package org.example.storeback.controller;

import org.example.storeback.controller.webmodel.request.CategoryInsertRequest;
import org.example.storeback.controller.webmodel.request.CategoryUpdateRequest;
import org.example.storeback.controller.webmodel.response.CategoryResponse;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.service.CategoryService;
import org.example.storeback.domain.service.dto.CategoryDto;
import org.example.storeback.domain.validation.DtoValidator;
import org.example.storeback.domain.exception.ValidationException;
import org.example.storeback.controller.mapper.CategoryMapperPresentation;
import org.example.storeback.domain.validation.RequiresRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequiresRole(Role.ADMIN)
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAllCategories(){

        List<CategoryDto> categories = categoryService.findAll();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(categoryDto -> new CategoryResponse(categoryDto.id(), categoryDto.name()))
                .toList();
        return ResponseEntity.ok(categoryResponses);
    }

    @GetMapping("/public")
    public ResponseEntity<List<CategoryResponse>> findAllCategoriesPublic(){

        List<CategoryDto> categories = categoryService.findAll();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(categoryDto -> new CategoryResponse(categoryDto.id(), categoryDto.name()))
                .toList();
        return ResponseEntity.ok(categoryResponses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findCategoryById(@PathVariable Long id) {
        Optional<CategoryDto> categoryDtoOptional = categoryService.findById(id);
        return categoryDtoOptional
                .map(categoryDto -> ResponseEntity.ok(new CategoryResponse(categoryDto.id(), categoryDto.name())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<CategoryResponse> findCategoryByName(@PathVariable String name) {
        Optional<CategoryDto> categoryDtoOptional = categoryService.findByName(name);
        return categoryDtoOptional
                .map(categoryDto -> ResponseEntity.ok(new CategoryResponse(categoryDto.id(), categoryDto.name())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @RequiresRole(Role.ADMIN)
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryInsertRequest request) {
        CategoryDto categoryToCreate = CategoryMapperPresentation.fromCategoryInsertToCategoryDto(request);
        try {
            DtoValidator.validate(categoryToCreate);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().build();
        }

        CategoryDto createdCategory = categoryService.save(categoryToCreate);
        CategoryResponse response = CategoryMapperPresentation.fromCategoryDtoToCategoryResponse(createdCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @RequiresRole(Role.ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateRequest request) {
        CategoryDto categoryToUpdate = CategoryMapperPresentation.fromCategoryUpdateToCategoryDto(request, id);
        try {
            DtoValidator.validate(categoryToUpdate);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().build();
        }

        Optional<CategoryDto> existing = categoryService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CategoryDto updated = categoryService.save(categoryToUpdate);
        CategoryResponse response = CategoryMapperPresentation.fromCategoryDtoToCategoryResponse(updated);
        return ResponseEntity.ok(response);
    }

    @RequiresRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
