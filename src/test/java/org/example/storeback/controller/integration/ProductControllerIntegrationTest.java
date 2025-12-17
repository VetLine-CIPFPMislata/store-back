package org.example.storeback.controller.integration;

import org.example.storeback.domain.models.Role;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @MockBean
//    private AuthService authService;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    private String adminToken = "admin-token";
//    private ClientDto adminUser;
//
//    @BeforeEach
//    void setUp() {
//        adminUser = new ClientDto(1L, "Admin", "admin@example.com", "pass", "", null, Role.ADMIN);
//        when(authService.getUserFromToken(anyString())).thenAnswer(invocation -> {
//            String token = invocation.getArgument(0, String.class);
//            if (token != null && token.equals(adminToken)) {
//                return Optional.of(adminUser);
//            }
//            return Optional.empty();
//        });
//
//        // Ensure a category exists (id 1) for products created by fixtures
//        CategoryEntity cat = new CategoryEntity(1L, ProductFixtures.sampleCategory().getName(), ProductFixtures.sampleCategory().getDescription());
//        categoryRepository.save(cat);
//    }
//
//    private HttpHeaders adminHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(adminToken);
//        return headers;
//    }
//
//    @Test
//    void createAndGetAndDeleteProduct_flow() {
//        // Create
//        ProductInsertRequest insert = ProductFixtures.sampleInsertRequest();
//        HttpEntity<ProductInsertRequest> req = new HttpEntity<>(insert, adminHeaders());
//        ResponseEntity<ProductResponse> createResp = restTemplate.postForEntity("/api/products", req, ProductResponse.class);
//        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        ProductResponse created = createResp.getBody();
//        assertThat(created).isNotNull();
//        assertThat(created.id()).isNotNull();
//
//        Long id = created.id();
//
//        // Get by id
//        ResponseEntity<ProductResponse> getResp = restTemplate.getForEntity("/api/products/" + id, ProductResponse.class);
//        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
//        ProductResponse fetched = getResp.getBody();
//        assertThat(fetched).isNotNull();
//        assertThat(fetched.id()).isEqualTo(id);
//        assertThat(fetched.name()).isEqualTo(insert.name());
//
//        // Update
//        ProductUpdateRequest update = new ProductUpdateRequest(id, "Updated name", "Desc", insert.category(), insert.pictureProduct(), insert.quantity(), insert.basePrice(), insert.discountPercentage());
//        HttpEntity<ProductUpdateRequest> updateReq = new HttpEntity<>(update, adminHeaders());
//        ResponseEntity<ProductResponse> updateResp = restTemplate.exchange("/api/products/" + id, HttpMethod.PUT, updateReq, ProductResponse.class);
//        assertThat(updateResp.getStatusCode()).isEqualTo(HttpStatus.OK);
//        ProductResponse updated = updateResp.getBody();
//        assertThat(updated).isNotNull();
//        assertThat(updated.name()).isEqualTo("Updated name");
//
//        // Delete
//        HttpEntity<Void> deleteReq = new HttpEntity<>(adminHeaders());
//        ResponseEntity<Void> deleteResp = restTemplate.exchange("/api/products/" + id, HttpMethod.DELETE, deleteReq, Void.class);
//        assertThat(deleteResp.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//
//        // Get after delete -> 404
//        ResponseEntity<ProductResponse> afterDelete = restTemplate.getForEntity("/api/products/" + id, ProductResponse.class);
//        assertThat(afterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    void getAllProducts_returnsPaged() {
//        // Create one product to ensure DB not empty
//        ProductInsertRequest insert = ProductFixtures.sampleInsertRequest();
//        HttpEntity<ProductInsertRequest> req = new HttpEntity<>(insert, adminHeaders());
//        restTemplate.postForEntity("/api/products", req, ProductResponse.class);
//
//        ResponseEntity<String> resp = restTemplate.getForEntity("/api/products", String.class);
//        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(resp.getBody()).contains("\"pageNumber\"", "\"data\"");
//    }
//
//    @Test
//    void getProductById_notFound_returns404() {
//        ResponseEntity<ProductResponse> resp = restTemplate.getForEntity("/api/products/999999", ProductResponse.class);
//        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }

}
