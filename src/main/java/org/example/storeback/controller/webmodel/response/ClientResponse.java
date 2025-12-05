package org.example.storeback.controller.webmodel.response;

public record ClientResponse (
        Long id,
        String name,
        String phone,
        String email,
        String password,
        Long cartId,
        String role
    ){}
