package com.example.desafio_picpay_simplificado.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServicoExternoForaDoArException extends RuntimeException{
    public ServicoExternoForaDoArException(String mensagem){
        super(mensagem);
    }
}
