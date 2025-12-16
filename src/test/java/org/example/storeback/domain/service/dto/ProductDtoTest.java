package org.example.storeback.domain.service.dto;

import jakarta.validation.*;
import org.example.storeback.domain.exception.ValidationException;
import org.example.storeback.domain.models.Category;

import org.example.storeback.domain.validation.DtoValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.*;

public class ProductDtoTest {

    @Test
    void productDto_creation_test(){

        Long id = 1L;
        Category category = new Category(1L, "Electronics", "Electronic devices");
        String name = "Test";
        String description = "Test description";
        BigDecimal price = new BigDecimal(100);
        BigDecimal basePrice = new BigDecimal(100);
        BigDecimal discountPercentage = new BigDecimal(10);
        String picture = "http://test.com";
        int quantity = 10;
        int rating = 5;

        ProductDto productDto = new ProductDto(id, category, name, description, basePrice, price, discountPercentage, picture, quantity, rating);

        assertEquals(id, productDto.id());
        assertEquals(category, productDto.category());
        assertEquals(name, productDto.name());
        assertEquals(description, productDto.productDescription());
        assertEquals(price, productDto.price());
        assertEquals(basePrice, productDto.basePrice());
        assertEquals(discountPercentage, productDto.discountPercentage());
        assertEquals(picture, productDto.pictureProduct());
        assertEquals(quantity, productDto.quantity());
        assertEquals(rating, productDto.rating());
    }

    @Test
    void productDtoValidation(){
        Long id = 1L;
        Category category = new Category(1L, "Electronics", "Electronic devices");
        String name = "Test";
        String description = "Test description";
        BigDecimal price = new BigDecimal(100);
        BigDecimal basePrice = new BigDecimal(100);
        BigDecimal discountPercentage = new BigDecimal(10);
        String picture = "http://test.com";
        int quantity = 10;
        int rating = 5;

        ProductDto productDto = new ProductDto(id, category, name, description, basePrice, price, discountPercentage, picture, quantity, rating);

        DtoValidator.validate(productDto);
    }

    @Test
    void productDtoInvalidRating(){
        Long id = 1L;
        Category category = new Category(1L, "Electronics", "Electronic devices");
        String name = "Test";
        String description = "Test description";
        BigDecimal price = new BigDecimal(100);
        BigDecimal basePrice = new BigDecimal(100);
        BigDecimal discountPercentage = new BigDecimal(10);
        String picture = "http://test.com";
        int quantity = 10;
        int invalidRating = 6; // Rating inválido
        ProductDto productDto = new ProductDto(id, category, name, description, basePrice, price, discountPercentage, picture, quantity, invalidRating);

        assertThrows(ValidationException.class, () -> DtoValidator.validate(productDto));

    }

    @Test
    void productDtoInvalidNullBasePrice(){
        Long id = 1L;
        Category category = new Category(1L, "Electronics", "Electronic devices");
        String name = "Test";
        String description = "Test description";
        BigDecimal price = new BigDecimal(100);
        BigDecimal basePrice = null;
        BigDecimal discountPercentage = new BigDecimal(10);
        String picture = "http://test.com";
        int quantity = 10;
        int rating = 5;
        ProductDto productDto = new ProductDto(id, category, name, description,
                basePrice, price, discountPercentage, picture, quantity, rating);
        assertThrows(ValidationException.class, () -> DtoValidator.validate(productDto));
    }

    @Test
    void productDtoInvalidDiscountPercentage(){
        Long id = 1L;
        Category category = new Category(1L, "Electronics", "Electronic devices");
        String name = "Test";
        String description = "Test description";
        BigDecimal price = new BigDecimal(100);
        BigDecimal basePrice = new BigDecimal(100);
        BigDecimal discountPercentage = new BigDecimal(-5);
        String picture = "http://test.com";
        int quantity = 10;
        int rating = 5;

        ProductDto productDto = new ProductDto(id, category, name, description, basePrice, price, discountPercentage, picture, quantity, rating);
        assertThrows(ValidationException.class, () -> DtoValidator.validate(productDto));
    }
    @Test
    void productDtoInvalidQuantity(){
    Long id = 1L;
    Category category = new Category(1L, "Electronics", "Electronic devices");
    String name = "Test";
    String description = "Test description";
    BigDecimal price = new BigDecimal(100);
    BigDecimal basePrice = new BigDecimal(100);
    BigDecimal discountPercentage = new BigDecimal(10);
    String picture = "http://test.com";
    int quantity = -10;
    int rating = 5;
    ProductDto productDto = new ProductDto(id, category, name, description, basePrice, price, discountPercentage, picture, quantity, rating);
    assertThrows(ValidationException.class, () -> DtoValidator.validate(productDto));
    }

    @Test
    void productDtoInvalidName(){
        Long id = 1L;
        Category category = new Category(1L, "Electronics", "Electronic devices");
        String name = "Producto de prueba diseñado para validar sistemas," +
                " formularios y bases de datos que requieren nombres extensos." +
                " Este artículo no representa un producto comercial real, " +
                "sino un identificador ficticio creado con fines técnicos, de desarrollo y verificación de límites de caracteres. " +
                "Puede utilizarse en entornos de prueba, demostraciones internas o simulaciones sin afectar información real.";
        String description = "Test description";
        BigDecimal price = new BigDecimal(100);
        BigDecimal basePrice = new BigDecimal(100);
        BigDecimal discountPercentage = new BigDecimal(10);
        String picture = "http://test.com";
        int quantity = 10;
        int rating = 5;

        ProductDto productDto = new ProductDto(id, category, name, description, basePrice, price, discountPercentage, picture, quantity, rating);
        assertThrows(ValidationException.class, () -> DtoValidator.validate(productDto));
    }

    @Test
    void productDtoInvalidDescription(){
        Long id = 1L;
        Category category = new Category(1L, "Electronics", "Electronic devices");
        String name = "Test";
        String description="""
                Este producto ha sido desarrollado con el objetivo de ofrecer una solución completa, versátil y fiable para usuarios que buscan calidad, durabilidad y facilidad de uso en su día a día. Su diseño combina funcionalidad y estética, permitiendo una integración natural en distintos entornos, ya sea en el hogar, en la oficina o en espacios profesionales. Cada componente ha sido cuidadosamente seleccionado y sometido a controles de calidad para garantizar un rendimiento constante a lo largo del tiempo.
                "Además, el producto incorpora características técnicas avanzadas que mejoran la experiencia del usuario y optimizan su funcionamiento. Estas prestaciones permiten un uso eficiente, reduciendo el consumo de recursos y facilitando el mantenimiento. Gracias a su estructura modular, es posible adaptarlo a diferentes necesidades sin comprometer su estabilidad ni su seguridad.                
                "La facilidad de instalación es otro de sus puntos fuertes, ya que no requiere conocimientos especializados ni herramientas complejas. Esto lo convierte en una opción ideal tanto para usuarios experimentados como para aquellos que se inician por primera vez. A su vez, el fabricante proporciona documentación clara y soporte técnico para resolver cualquier duda que pueda surgir durante el proceso de configuración o uso.
                "En términos de sostenibilidad, este producto ha sido concebido teniendo en cuenta el impacto ambiental. Se han utilizado materiales responsables y procesos de fabricación optimizados que contribuyen a reducir la huella ecológica. De esta manera, no solo se obtiene un artículo eficiente y funcional, sino también alineado con valores de respeto y cuidado del entorno.     
                "En conclusión, este producto representa una alternativa sólida y confiable dentro de su categoría. Su equilibrio entre diseño, rendimiento y sostenibilidad lo convierte en una elección acertada para quienes buscan una solución duradera, adaptable y preparada para responder a las exigencias actuales y futuras del mercado.
                """;
        BigDecimal price = new BigDecimal(100);
        BigDecimal basePrice = new BigDecimal(100);
        BigDecimal discountPercentage = new BigDecimal(10);
        String picture = "http://test.com";
        int quantity = 10;
        int rating = 5;

        ProductDto productDto = new ProductDto(id, category, name, description, basePrice, price, discountPercentage, picture, quantity, rating);
        assertThrows(ValidationException.class, () -> DtoValidator.validate(productDto));
    }
    @Test
    void productDtoInvalidCategory(){
        Long id = 1L;
        Category category = null;
        String name = "Test";
        String description = "Test description";
        BigDecimal price = new BigDecimal(100);
        BigDecimal basePrice = new BigDecimal(100);
        BigDecimal discountPercentage = new BigDecimal(10);
        String picture = "http://test.com";
        int quantity = 10;
        int rating = 5;
        ProductDto productDto = new ProductDto(id, category, name, description, basePrice, price, discountPercentage, picture, quantity, rating);
        assertThrows(ValidationException.class, () -> DtoValidator.validate(productDto));
    }

}
