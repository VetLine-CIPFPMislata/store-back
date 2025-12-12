package org.example.storeback.domain.service.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.storeback.domain.models.Role;

public record ClientDto(
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
        String name,

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email debe tener un formato válido")
        @Size(max = 255, message = "El email no puede exceder 255 caracteres")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(max = 255, message = "La contraseña no puede exceder 255 caracteres")
        String password,

        @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
        String phone,

        Long cartId,

        @NotNull(message = "El rol no puede estar vacío")
        Role role
) {
}
