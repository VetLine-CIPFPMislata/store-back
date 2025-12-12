package org.example.storeback.domain.repository.entity;

import org.example.storeback.domain.models.Role;

public record ClientEntity(
        Long id,
        String name,
        String email,
        String password,
        String phone,
        Long cartId,
        Role role){

}
