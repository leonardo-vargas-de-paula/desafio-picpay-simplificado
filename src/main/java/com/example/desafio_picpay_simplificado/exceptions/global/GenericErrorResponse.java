package com.example.desafio_picpay_simplificado.exceptions.global;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record GenericErrorResponse(HttpStatus status, String message, LocalDateTime timestamp, String error) {


}
