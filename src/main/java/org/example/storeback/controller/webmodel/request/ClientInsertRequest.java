package org.example.storeback.controller.webmodel.request;

public record ClientInsertRequest(
        String name,
        String phone,
        String email,
        String password,
        Long cartId,
        String role
) {
}
