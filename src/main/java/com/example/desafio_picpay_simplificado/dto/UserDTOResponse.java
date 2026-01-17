package com.example.desafio_picpay_simplificado.dto;

import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.model.user.UserType;

import java.math.BigDecimal;

public record UserDTOResponse (String firstName, String lastName, String document, BigDecimal balance, String email, UserType userType) {

    public static UserDTOResponse fromEntity(User user) {
        return new UserDTOResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getDocument(),
                user.getBalance(),
                user.getEmail(),
                user.getUserType()
        );
    }

    public static UserDTOResponse fromDTO(UserDTO user) {
        return new UserDTOResponse(
                user.firstName(),
                user.lastName(),
                user.document(),
                user.balance(),
                user.email(),
                user.userType()
        );
    }

}
