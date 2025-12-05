package org.example.storeback.controller.webmodel.request;

public record ClientUpdateRequest(
        Long id,
        String name,
        String phone,
        String email,
        String password,
        Long cartId
) {
}
