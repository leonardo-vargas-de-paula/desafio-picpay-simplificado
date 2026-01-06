package com.example.desafio_picpay_simplificado.dto;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long sender,Long receiver) {

}
