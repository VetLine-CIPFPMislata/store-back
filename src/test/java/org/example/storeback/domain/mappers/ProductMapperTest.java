package org.example.storeback.domain.mappers;

import org.example.storeback.domain.models.Product;
import org.example.storeback.domain.service.dto.ProductDto;
import org.example.storeback.testutil.ProductFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class ProductMapperTest {

    @Test
    @DisplayName("Producto a ProductoDto")
    void ProductToProductDto() {
        Product product = ProductFixtures.sampleProductDomain();

        ProductDto dto = ProductMapper.getInstance().fromProductToProductDto(product);
        assertNotNull(dto);
        assertEquals(product.getId(), dto.id());
        assertEquals(product.getName(), dto.name());
        assertEquals(product.getProductDescription(), dto.productDescription());
        assertEquals(product.getBaseprice(), dto.basePrice());
        assertEquals(product.getDiscountPercentage(), dto.discountPercentage());
        assertEquals(product.getPictureProduct(), dto.pictureProduct());
        assertEquals(product.getQuantity(), dto.quantity());
        assertEquals(product.getRating(), dto.rating());
    }


    @Test
    @DisplayName("Producto Entity a Producto")
    void ProductEntityToProduct() {
        var entity = ProductFixtures.sampleProductEntity();
        Product product = ProductMapper.getInstance().fromProductEntityToProduct(entity);
        assertNotNull(product);
        assertEquals(entity.id(), product.getId());
        assertEquals(entity.name(), product.getName());
        assertEquals(entity.productDescription(), product.getProductDescription());
        assertEquals(entity.basePrice(), product.getBaseprice());
        assertEquals(entity.discountPercentage(), product.getDiscountPercentage());
        assertEquals(entity.pictureProduct(), product.getPictureProduct());
        assertEquals(entity.quantity(), product.getQuantity());
        assertEquals(entity.rating(), product.getRating());
    }

    @Test
    @DisplayName("Producto a Producto Entity")
    void ProductToProductEntity(){
        Product product = ProductFixtures.sampleProductDomain();
        var entity = ProductMapper.getInstance().fromProductToProductEntity(product);
        assertNotNull(entity);
        assertEquals(product.getId(), entity.id());
        assertEquals(product.getName(), entity.name());
        assertEquals(product.getProductDescription(), entity.productDescription());
        assertEquals(product.getBaseprice(), entity.basePrice());
        assertEquals(product.getDiscountPercentage(), entity.discountPercentage());
        assertEquals(product.getPictureProduct(), entity.pictureProduct());
        assertEquals(product.getQuantity(), entity.quantity());
        assertEquals(product.getRating(), entity.rating());
    }
    @Test
    @DisplayName("Producto Dto a Producto")
    void ProductDtoToProduct() {
        ProductDto dto = ProductFixtures.sampleProductDto();
        Product product = ProductMapper.getInstance().fromProductDtoToProduct(dto);
        assertNotNull(product);
        assertEquals(dto.id(), product.getId());
        assertEquals(dto.name(), product.getName());
        assertEquals(dto.productDescription(), product.getProductDescription());
        assertEquals(dto.basePrice(), product.getBaseprice());
        assertEquals(dto.discountPercentage(), product.getDiscountPercentage());
        assertEquals(dto.pictureProduct(), product.getPictureProduct());
        assertEquals(dto.quantity(), product.getQuantity());
        assertEquals(dto.rating(), product.getRating());
    }

    @Test
    @DisplayName("fromProductToProductDto lanza IllegalArgumentException cuando el producto es null")
    void fromProductToProductDto_null_throws() {
        Executable exec = () -> ProductMapper.getInstance().fromProductToProductDto(null);
        assertThrows(IllegalArgumentException.class, exec);
    }

    @Test
    @DisplayName("fromProductDtoToProduct lanza IllegalArgumentException cuando el dto es null")
    void fromProductDtoToProduct_null_throws() {
        Executable exec = () -> ProductMapper.getInstance().fromProductDtoToProduct(null);
        assertThrows(IllegalArgumentException.class, exec);
    }

    @Test
    @DisplayName("fromProductEntityToProduct lanza IllegalArgumentException cuando la entidad es null")
    void fromProductEntityToProduct_null_throws() {
        Executable exec = () -> ProductMapper.getInstance().fromProductEntityToProduct(null);
        assertThrows(IllegalArgumentException.class, exec);
    }

    @Test
    @DisplayName("fromProductToProductEntity lanza IllegalArgumentException cuando el producto es null")
    void fromProductToProductEntity_null_throws() {
        Executable exec = () -> ProductMapper.getInstance().fromProductToProductEntity(null);
        assertThrows(IllegalArgumentException.class, exec);
    }
}
