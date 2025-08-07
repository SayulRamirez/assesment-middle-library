package com.gateway.library.dao;

import jakarta.validation.constraints.*;

public record RegisterRequest(

        @NotBlank(message = "El campo no debe de estar vacío")
        String names,

        @Email(message = "Debe de ser un email valido")
        @NotBlank(message = "El campo no debe de estar vacío")
        String username,

        @NotBlank(message = "El campo no debe de estar vacío")
        @Size(min = 8, max = 20, message = "El password debe de tener entre 8 y 20 caracteres")
        String password
) {
}
