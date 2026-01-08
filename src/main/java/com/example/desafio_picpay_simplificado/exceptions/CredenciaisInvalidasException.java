package com.example.desafio_picpay_simplificado.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CredenciaisInvalidasException extends RuntimeException{

    public CredenciaisInvalidasException(String mensagem){
        super(mensagem);
    }
}
