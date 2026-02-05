package org.example.storeback.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private final Long id;
    private final Integer totalProducts;
    private final BigDecimal totalPrice;
    private final OrderState state;
    private final Client user;
    private final LocalDateTime createdAt;
    private final LocalDateTime orderAt;
    private final String address;
    private final List<OrderItem> items;

    public Order(Long id, Integer totalProducts, BigDecimal totalPrice, OrderState state,
                 Client user, LocalDateTime createdAt, LocalDateTime orderAt,
                 String address, List<OrderItem> items) {
        this.id = id;
        this.totalProducts = totalProducts;
        this.totalPrice = totalPrice;
        this.state = state;
        this.user = user;
        this.createdAt = createdAt;
        this.orderAt = orderAt;
        this.address = address;
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

    public OrderState getState() {
        return state;
    }

    public Client getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}

