package com.loan.microservice.service;

import com.loan.microservice.document.Loan;
import com.loan.microservice.dto.LoanResponse;
import com.loan.microservice.exception.DocumentNotFoundException;
import com.loan.microservice.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;

    private static final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Override
    public LoanResponse addLoan(int idBook, int idUser) {

        log.info("Realizando un nuevo prestamo del libro: {}", idBook);

        LocalDate now = LocalDate.now();

        Loan loan = Loan.builder()
                .userId(idUser)
                .book(idBook)
                .loanDate(now)
                .loanDueDate(now.plusDays(7L))
                .borrowed(false)
                .build();

        Loan save = repository.save(loan);

        return documentToDto(save);
    }

    @Override
    public void toTurnIn(int idLoan) {
        Loan loan = repository.findById(String.valueOf(idLoan))
                .orElseThrow(() -> new DocumentNotFoundException("No se encontro el prestamo con id: " + idLoan));

        log.info("Actualizando el estatus del prestamo: {}", idLoan);

        loan.setBorrowed(true);

        repository.save(loan);
    }

    @Override
    public List<LoanResponse> findLoansByIdUser(int idUser) {
        return repository.findAllByUserId(idUser).stream()
                .map(this::documentToDto)
                .toList();
    }

    private LoanResponse documentToDto(Loan loan) {
        return new LoanResponse(loan.getId(),
                loan.getBook(),
                loan.getLoanDueDate(),
                loan.isBorrowed());
    }
}
