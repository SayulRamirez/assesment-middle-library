package com.loan.microservice.dto;

import java.time.LocalDate;

public record LoanResponse(
        String idLoan,
        int idBook,
        LocalDate loanDueDate,
        boolean borrowed
) {
}
