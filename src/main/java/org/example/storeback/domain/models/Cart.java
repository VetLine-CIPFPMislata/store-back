package org.example.storeback.domain.models;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private final Long id;
    private final Integer totalProducts;
    private final BigDecimal totalPrice;
    private final Client user;
    private final List<CartItem> items;

    public Cart(Long id, Integer totalProducts, BigDecimal totalPrice, Client user, List<CartItem> items) {
        this.id = id;
        this.totalProducts = totalProducts;
        this.totalPrice = totalPrice;
        this.user = user;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Client getUser() {
        return user;
    }

    public List<CartItem> getItems() {
        return items;
    }
}
