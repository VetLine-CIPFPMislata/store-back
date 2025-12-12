package org.example.storeback.controller.webmodel.request;

import org.example.storeback.domain.models.Role;

public record ClientUpdateRequest(
        String name,
        String phone,
        String email,
        String password,
        Long cartId,
        Role role
) {
}
