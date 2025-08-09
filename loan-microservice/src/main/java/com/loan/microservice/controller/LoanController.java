package com.loan.microservice.controller;

import com.loan.microservice.dto.LoanRequest;
import com.loan.microservice.dto.LoanResponse;
import com.loan.microservice.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService service;

    @PostMapping
    public ResponseEntity<LoanResponse> addLoan(@Valid @RequestBody LoanRequest request) {
        return ResponseEntity.ok(service.addLoan(request.idBook(), request.idUser()));
    }

    @PutMapping("{idLoan}")
    public ResponseEntity<Void> toTurnIn(@PathVariable String idLoan) {
        service.toTurnIn(idLoan);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{idUser}")
    public ResponseEntity<List<LoanResponse>> findAllByIdUser(@PathVariable Integer idUser) {
        return ResponseEntity.ok(service.findLoansByIdUser(idUser));
    }
}
