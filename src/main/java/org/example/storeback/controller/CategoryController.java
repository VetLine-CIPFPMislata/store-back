package org.example.storeback.controller;

import org.example.storeback.controller.webmodel.request.CategoryInsertRequest;
import org.example.storeback.controller.webmodel.response.CategoryResponse;
import org.example.storeback.domain.service.CategoryService;
import org.example.storeback.domain.service.dto.CategoryDto;
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

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAllCategories(){

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
    // GET by Name
    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> findCategoryByName(@RequestParam String name) {
        Optional<CategoryDto> categoryDtoOptional = categoryService.findByName(name);
        return categoryDtoOptional
                .map(categoryDto -> ResponseEntity.ok(new CategoryResponse(categoryDto.id(), categoryDto.name())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryInsertRequest request) {
        CategoryDto categoryToCreate = new CategoryDto(null, request.name(), request.description());
        CategoryDto createdCategory = categoryService.save(categoryToCreate);
        CategoryResponse response = new CategoryResponse(createdCategory.id(), createdCategory.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
