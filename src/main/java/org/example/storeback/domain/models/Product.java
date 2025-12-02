package org.example.storeback.domain.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Product {
    private final Long id;
    private final Category category;
    private final String name;
    private final String productDescription;
    private final BigDecimal price;
    private final BigDecimal basePrice;
    private final BigDecimal discountPercentage;
    private final String pictureProduct;
    private final int quantity;
    private final int rating;

    public Product(Long id,
                   Category category,
                   String name,
                   String productDescription,
                   BigDecimal basePrice,
                   BigDecimal price,
                   BigDecimal discountPercentage,
                   String pictureProduct,
                   int quantity,
                   int rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("rating tiene que estar entre 0 y 5");
        }
        this.id = id;
        this.category = category;
        this.name = name;
        this.productDescription = productDescription;
        this.basePrice = basePrice;
        this.price = calculateFinalPrice();
        this.discountPercentage = discountPercentage;
        this.pictureProduct = pictureProduct;
        this.quantity = quantity;
        this.rating = rating;
    }

    public Long getId() { return id; }
    public Category getCategory() { return category; }
    public String getName() { return name; }
    public String getProductDescription() { return productDescription; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getBaseprice() { return basePrice; }
    public BigDecimal getDiscountPercentage() { return discountPercentage; }
    public String getPictureProduct() { return pictureProduct; }
    public int getQuantity() { return quantity; }
    public int getRating() { return rating; }


    public BigDecimal calculateFinalPrice() {
        if( basePrice == null ) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal discount = basePrice
                .multiply(discountPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return basePrice.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }
}
