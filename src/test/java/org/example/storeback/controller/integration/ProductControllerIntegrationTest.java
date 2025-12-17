package org.example.storeback.controller.integration;

import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.models.Category;
import org.example.storeback.domain.repository.CategoryRepository;
import org.example.storeback.domain.repository.entity.CategoryEntity;
import org.example.storeback.domain.service.AuthService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.testutil.ProductFixtures;
import org.example.storeback.controller.webmodel.response.ProductResponse;
import org.example.storeback.controller.webmodel.request.ProductInsertRequest;
import org.example.storeback.controller.webmodel.request.ProductUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private AuthService authService;

    @Autowired
    private CategoryRepository categoryRepository;

    private String adminToken = "admin-token";
    private ClientDto adminUser;

    // Helper: obtiene o crea la categoría 'Cat1' y devuelve su entidad con id real
    private CategoryEntity getOrCreateCat1() {
        return categoryRepository.findByName(ProductFixtures.sampleCategory().getName())
                .orElseGet(() -> {
                    CategoryEntity cat = new CategoryEntity(null,
                            ProductFixtures.sampleCategory().getName(),
                            ProductFixtures.sampleCategory().getDescription());
                    return categoryRepository.save(cat);
                });
    }

    @BeforeEach
    void setUp() {
        adminUser = new ClientDto(1L, "Admin", "admin@example.com", "pass", "", null, Role.ADMIN);
        when(authService.getUserFromToken(anyString())).thenAnswer(invocation -> {
            String token = invocation.getArgument(0, String.class);
            if (token != null && token.equals(adminToken)) {
                return Optional.of(adminUser);
            }
            return Optional.empty();
        });

        // Ensure a category exists for products created by fixtures
        // Only create if it doesn't exist to avoid unique constraint violations
        if (categoryRepository.findByName(ProductFixtures.sampleCategory().getName()).isEmpty()) {
            CategoryEntity cat = new CategoryEntity(null, ProductFixtures.sampleCategory().getName(), ProductFixtures.sampleCategory().getDescription());
            categoryRepository.save(cat);
        }
    }

    private HttpHeaders adminHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);
        return headers;
    }

    @Test
    void createAndGetAndDeleteProduct_flow() {
        // Crear categoría y usar su id real en el request
        CategoryEntity cat = getOrCreateCat1();
        Category categoryDomain = new Category(cat.id(), cat.name(), cat.description());
        ProductInsertRequest insert = new ProductInsertRequest(
                "Product 1",
                "Desc",
                categoryDomain,
                "http://img",
                5,
                new java.math.BigDecimal("10.00"),
                new java.math.BigDecimal("5.00")
        );
        HttpEntity<ProductInsertRequest> req = new HttpEntity<>(insert, adminHeaders());
        ResponseEntity<ProductResponse> createResp = restTemplate.postForEntity("/api/products", req, ProductResponse.class);
        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ProductResponse created = createResp.getBody();
        assertThat(created).isNotNull();
        assertThat(created.id()).isNotNull();

        Long id = created.id();

        // Get by id
        ResponseEntity<ProductResponse> getResp = restTemplate.getForEntity("/api/products/" + id, ProductResponse.class);
        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductResponse fetched = getResp.getBody();
        assertThat(fetched).isNotNull();
        assertThat(fetched.id()).isEqualTo(id);
        assertThat(fetched.name()).isEqualTo(insert.name());

        // Update (mantener la misma categoría real)
        ProductUpdateRequest update = new ProductUpdateRequest(id, "Updated name", "Desc", categoryDomain, insert.pictureProduct(), insert.quantity(), insert.basePrice(), insert.discountPercentage());
        HttpEntity<ProductUpdateRequest> updateReq = new HttpEntity<>(update, adminHeaders());
        ResponseEntity<ProductResponse> updateResp = restTemplate.exchange("/api/products/" + id, HttpMethod.PUT, updateReq, ProductResponse.class);
        assertThat(updateResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductResponse updated = updateResp.getBody();
        assertThat(updated).isNotNull();
        assertThat(updated.name()).isEqualTo("Updated name");

        // Delete
        HttpEntity<Void> deleteReq = new HttpEntity<>(adminHeaders());
        ResponseEntity<Void> deleteResp = restTemplate.exchange("/api/products/" + id, HttpMethod.DELETE, deleteReq, Void.class);
        assertThat(deleteResp.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // Get after delete -> 404
        ResponseEntity<ProductResponse> afterDelete = restTemplate.getForEntity("/api/products/" + id, ProductResponse.class);
        assertThat(afterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getAllProducts_returnsPaged() {
        // Crear categoría y usar su id real en el request del producto
        CategoryEntity cat = getOrCreateCat1();
        Category categoryDomain = new Category(cat.id(), cat.name(), cat.description());
        ProductInsertRequest insert = new ProductInsertRequest(
                "Product 1",
                "Desc",
                categoryDomain,
                "http://img",
                5,
                new java.math.BigDecimal("10.00"),
                new java.math.BigDecimal("5.00")
        );
        HttpEntity<ProductInsertRequest> req = new HttpEntity<>(insert, adminHeaders());
        restTemplate.postForEntity("/api/products", req, ProductResponse.class);

        ResponseEntity<String> resp = restTemplate.getForEntity("/api/products", String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).contains("\"pageNumber\"", "\"data\"");
    }

    @Test
    void getProductById_notFound_returns404() {
        ResponseEntity<ProductResponse> resp = restTemplate.getForEntity("/api/products/999999", ProductResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
