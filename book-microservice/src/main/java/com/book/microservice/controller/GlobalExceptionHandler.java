package com.book.microservice.controller;

import com.book.microservice.exception.BookNotFoundException;
import com.book.microservice.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handlerResourceNotFoundException(ResourceNotFoundException e) {
        Map<String, Object> response = new LinkedHashMap<>(3);

        response.put("timestamp", LocalDateTime.now());
        response.put("message", e.getMessage());
        response.put("code", HttpStatus.NOT_FOUND.value());

        log.warn("Excepcion de recurso: {}", e.getMessage());

        return response;
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handlerBookNotFoundException(BookNotFoundException e) {
        Map<String, Object> response = new LinkedHashMap<>(3);

        response.put("timestamp", LocalDateTime.now());
        response.put("message", e.getMessage());
        response.put("code", HttpStatus.NOT_FOUND.value());

        log.warn("Excepcion: {}", e.getMessage());

        return response;
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleFileNotFoundException(FileNotFoundException e) {
        Map<String, Object> response = new LinkedHashMap<>(3);

        response.put("timestamp", LocalDateTime.now());
        response.put("message", "No se encontro el recurso buscado");
        response.put("code", HttpStatus.NOT_FOUND.value());

        log.warn("Excepcion de archivo: {}", e.getMessage());

        return response;
    }
}
