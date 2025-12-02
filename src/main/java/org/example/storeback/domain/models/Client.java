package org.example.storeback.domain.models;

import java.time.LocalDate;

public class Client {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final String phone;
    private final String address;
    private final LocalDate birthdate;
    private final String country;
    private final Long cartId;

    public Client(Long id,
                  String name,
                  String email,
                  String password,
                  String phone,
                  String address,
                  LocalDate birthdate,
                  String country,
                  Long cartId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.birthdate = birthdate;
        this.country = country;
        this.cartId = cartId;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public LocalDate getBirthdate() { return birthdate; }
    public String getCountry() { return country; }
    public Long getCartId() { return cartId; }
}