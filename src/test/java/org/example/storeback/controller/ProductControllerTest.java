package org.example.storeback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.storeback.domain.models.Page;
import org.example.storeback.domain.service.ProductService;
import org.example.storeback.domain.service.AuthService;
import org.example.storeback.domain.service.dto.ProductDto;
import org.example.storeback.testutil.ProductFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = ProductController.class)
@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProductById_found_returns200() throws Exception{
        ProductDto dto = ProductFixtures.sampleProductDto();
        when(productService.findById(1L)).thenReturn(java.util.Optional.of(dto));

        mockMvc.perform(get("/api/products/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.id()));
    }

    @Test
    void getProductById_notFound_returns404() throws Exception{
        when(productService.findById(999L)).thenReturn(java.util.Optional.empty());
        mockMvc.perform(get("/api/products/999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllProducts_returnsPaged() throws Exception{
        ProductDto dto = ProductFixtures.sampleProductDto();
        Page<ProductDto> page = new Page<>(List.of(dto),1,10,1);
        when(productService.findAll(1,10)).thenReturn(page);
        mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(dto.id()))
                .andExpect(jsonPath("$.pageNumber").value(1))
                .andExpect(jsonPath("$.pageSize").value(10));
    }

}
