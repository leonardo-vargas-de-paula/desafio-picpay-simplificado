package com.example.desafio_picpay_simplificado.dto;

import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.model.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getDocument(),
                user.getBalance(),
                user.getEmail(),
                user.getPassword(),
                user.getUserType()
        );
    }

}
