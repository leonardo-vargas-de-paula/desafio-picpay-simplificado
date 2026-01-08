package com.example.desafio_picpay_simplificado.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailJaCadastradoException extends RuntimeException{

    public EmailJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
