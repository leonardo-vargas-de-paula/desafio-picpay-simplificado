package com.example.desafio_picpay_simplificado.exceptions;

public class TransacaoNaoAutorizadaException extends RuntimeException{
    public TransacaoNaoAutorizadaException(String mensagem){
        super(mensagem);
    }
}
