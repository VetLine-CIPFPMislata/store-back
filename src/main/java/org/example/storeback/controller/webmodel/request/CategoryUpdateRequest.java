package org.example.storeback.controller.webmodel.request;

public record CategoryUpdateRequest(
        String name,
        String description
) {
}
