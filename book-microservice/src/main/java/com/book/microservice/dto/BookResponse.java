package com.book.microservice.dto;

public record BookResponse (
        Integer id,

        String title,

        String summary,

        String names,

        String genere
) {
}
