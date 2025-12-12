package org.example.storeback.domain.repository.entity;

import java.time.LocalDateTime;

public record SessionEntity(
        Long id,
        Long clientId,
        String token,
        LocalDateTime loginDate
) {
}

