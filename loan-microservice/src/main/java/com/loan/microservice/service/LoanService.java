package com.loan.microservice.service;

import com.loan.microservice.dto.LoanResponse;

import java.util.List;

public interface LoanService {

    LoanResponse addLoan(int idBook, int idUser);

    void toTurnIn(String idLoan);

    List<LoanResponse> findLoansByIdUser(int idUser);
}
