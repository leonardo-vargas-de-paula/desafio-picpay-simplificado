package com.example.desafio_picpay_simplificado.exceptions.global;

import com.example.desafio_picpay_simplificado.exceptions.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message, LocalDateTime timestamp, String error) {
        GenericErrorResponse response = new GenericErrorResponse(status, message, timestamp, error);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Object> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex){
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), LocalDateTime.now(), "Erro interno do servidor");
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    public ResponseEntity<Object> handleOperacaoNaoPermitida(OperacaoNaoPermitidaException ex){
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), LocalDateTime.now(), null);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Object> handleSaldoInsuficiente(SaldoInsuficienteException ex){
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now(), null);
    }

    @ExceptionHandler(TransacaoNaoAutorizadaException.class)
    public ResponseEntity<Object> handleTransacaoNaoAutorizada(TransacaoNaoAutorizadaException ex){
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), LocalDateTime.now(), null);

    }

    @ExceptionHandler(ServicoExternoForaDoArException.class)
    public ResponseEntity<Object> handleServicoExternoForaDoAr(ServicoExternoForaDoArException ex){
        return buildResponse(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), LocalDateTime.now(), null);
    }

    @ExceptionHandler(CPFOuCNPJJaCadastradoException.class)
    public ResponseEntity<Object> handleCPFOuCNPJJaCadastrado(CPFOuCNPJJaCadastradoException ex){
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), LocalDateTime.now(), null);

    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Object> handleEmailJaCadastrado(EmailJaCadastradoException ex){
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), LocalDateTime.now(), null);
    }



    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<Object> handleCredenciaisInvalidas(CredenciaisInvalidasException ex){
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), LocalDateTime.now(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        body.put("errors", errors);
        body.put("message", "Erro de validação nos campos enviados");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
