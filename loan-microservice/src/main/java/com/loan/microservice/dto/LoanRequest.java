package com.loan.microservice.dto;

public record LoanRequest(
        Integer idBook,
        Integer idUser
) {}
