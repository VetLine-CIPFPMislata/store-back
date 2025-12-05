package org.example.storeback.controller.webmodel.request;

public record ClientUpdateRequest(
        String name,
        String phone,
        String email,
        String password,
        Long cartId,
        String role
) {
}
