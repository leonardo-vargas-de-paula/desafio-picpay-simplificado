package com.example.desafio_picpay_simplificado.dto;

import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.model.user.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UserDTO(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String document,
                       BigDecimal balance, @NotBlank  String email, @NotBlank  String password, @NotNull UserType userType) {

}
