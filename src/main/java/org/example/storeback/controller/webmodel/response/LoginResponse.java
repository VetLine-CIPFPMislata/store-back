package org.example.storeback.controller.webmodel.response;

import org.example.storeback.domain.models.Role;

public record LoginResponse(
        String token,
        String email,
        String name,
        Role role
) {
}
