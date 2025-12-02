package org.example.storeback.domain.service.dto;

import java.time.LocalDate;

public record ClientDto(
        Long id,
        String name,
        String email,
        String password,
        String phone,
        String address,
        LocalDate birthdate,
        String country,
        Long cartId
) {
}
