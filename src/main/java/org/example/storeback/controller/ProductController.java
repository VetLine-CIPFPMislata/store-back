package org.example.storeback.controller;

import org.example.storeback.controller.mapper.ProductMapperPresentation;
import org.example.storeback.controller.webmodel.request.ProductInsertRequest;
import org.example.storeback.controller.webmodel.request.ProductUpdateRequest;
import org.example.storeback.controller.webmodel.response.ProductResponse;
import org.example.storeback.domain.models.Category;
import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.service.ProductService;
import org.example.storeback.domain.service.dto.ProductDto;
import org.example.storeback.domain.validation.DtoValidator;
import org.example.storeback.domain.validation.RequiresRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequiresRole(Role.ADMIN)
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<ProductDto> products = productService.findAll(page, size);
        List<ProductResponse> responses = products.data().stream()
                .map(ProductMapperPresentation.getInstance()::fromDtoToResponse)
                .toList();
        Page<ProductResponse> responsePage = new Page<>(
                responses,
                products.pageNumber(),
                products.pageSize(),
                products.totalElements()
        );
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/public")
    public ResponseEntity<Page<ProductResponse>> getAllProductsPublic(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<ProductDto> products = productService.findAll(page, size);
        List<ProductResponse> responses = products.data().stream()
                .map(ProductMapperPresentation.getInstance()::fromDtoToResponse)
                .toList();
        Page<ProductResponse> responsePage = new Page<>(
                responses,
                products.pageNumber(),
                products.pageSize(),
                products.totalElements()
        );
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ProductMapperPresentation.getInstance()::fromDtoToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<ProductResponse> getProductByName(@PathVariable String name) {
        return productService.findByName(name)
                .map(ProductMapperPresentation.getInstance()::fromDtoToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/category/{category}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable String category) {
        List<ProductDto> products = productService.findByCategory(category);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ProductResponse> responses = products.stream()
                .map(ProductMapperPresentation.getInstance()::fromDtoToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search/rating/{min}/{max}")
    public ResponseEntity<List<ProductResponse>> getProductByRating(
            @PathVariable int min,
            @PathVariable int max
    ) {
        List<ProductDto> products = productService.findByRating(min, max);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ProductResponse> responses = products.stream()
                .map(ProductMapperPresentation.getInstance()::fromDtoToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @RequiresRole(Role.ADMIN)
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductInsertRequest request) {
        ProductDto productDto = ProductMapperPresentation.getInstance().fromInsertRequestToDto(request);
        DtoValidator.validate(productDto);
        ProductDto createdProduct = productService.save(productDto);
        ProductResponse response = ProductMapperPresentation.getInstance().fromDtoToResponse(createdProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequiresRole(Role.ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateRequest request
    ) {
        return productService.findById(id)
                .map(existing -> {
                    ProductDto productDto = ProductMapperPresentation.getInstance().fromUpdateRequestToDto(request);
                    DtoValidator.validate(productDto);
                    ProductDto updated = productService.save(productDto);
                    ProductResponse response = ProductMapperPresentation.getInstance().fromDtoToResponse(updated);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @RequiresRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> {
                    productService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
