package org.example.storeback.domain.models;

public class CartItem {
    private final Long id;
    private final Integer quantity;
    private final Long cartId;
    private final Product product;

    public CartItem(Long id, Integer quantity, Long cartId, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.cartId = cartId;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public Product getProduct() {
        return product;
    }
}
