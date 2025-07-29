package com.gateway.library.dao;

import jakarta.validation.constraints.*;

public record RegisterRequest(

        @NotBlank(message = "El campo no debe de estar vacío")
        String names,

        @Email(message = "Debe de ser un email valido")
        @NotBlank(message = "El campo no debe de estar vacío")
        String username,

        @NotBlank(message = "The field cannot be empty")
        @Size(min = 8, max = 10, message = "Must contain between 8 and 10 characters")
        String password
) {
}
