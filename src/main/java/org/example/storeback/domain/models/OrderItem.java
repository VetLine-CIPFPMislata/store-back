package org.example.storeback.domain.models;

public class OrderItem {
    private final Long id;
    private final Integer quantity;
    private final Long orderId;
    private final Product product;

    public OrderItem(Long id, Integer quantity, Long orderId, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.orderId = orderId;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }
}

