package org.example.storeback.domain.repository.entity;

public record ClientEntity(
        Long id,
        String name,
        String email,
        String password,
        String phone,
        String address,
        String profilePicture,
        String country,
        Long cartId){

}
