package com.example.desafio_picpay_simplificado.dto;

import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.model.user.UserType;

import java.math.BigDecimal;

public record UserDTOResponse (String firstName, String lastName, String document, BigDecimal balance, String email, UserType userType) {

}
