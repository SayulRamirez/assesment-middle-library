package com.loan.microservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoanRequest(

        @NotNull
        @Positive(message = "El valor debe de ser mayor a 0")
        Integer idBook,

        @NotNull
        @Positive(message = "El valor debe de ser mayor a 0")
        Integer idUser
) {
}
