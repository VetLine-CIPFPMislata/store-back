package org.example.storeback.domain.models;

import java.time.LocalDate;

public class Client {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final String phone;
    private final Long cartId;
    private final Role role;

    public Client(Long id,
                  String name,
                  String email,
                  String password,
                  String phone,
                  Long cartId,
                  Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.cartId = cartId;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public Long getCartId() { return cartId; }
    public Role getRole() { return role; }
}