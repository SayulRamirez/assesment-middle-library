package com.loan.microservice.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "loans")
public class Loan {

    @Id
    private String id;

    private Integer userId;

    private Integer book;

    private LocalDate loanDate;

    private LocalDate loanDueDate;

    private boolean borrowed;
}
