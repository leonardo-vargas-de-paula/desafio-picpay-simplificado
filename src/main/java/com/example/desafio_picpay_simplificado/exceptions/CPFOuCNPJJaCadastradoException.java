package com.example.desafio_picpay_simplificado.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CPFOuCNPJJaCadastradoException extends RuntimeException{

    public CPFOuCNPJJaCadastradoException(String mensagem){super(mensagem);}

}
