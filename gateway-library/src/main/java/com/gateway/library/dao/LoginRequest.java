package com.gateway.library.dao;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(

        @NotEmpty(message = "El campo no debe de estar vacío")
        String username,

        @NotEmpty(message = "El campo no debe de estar vacío")
        String password
) {
}

