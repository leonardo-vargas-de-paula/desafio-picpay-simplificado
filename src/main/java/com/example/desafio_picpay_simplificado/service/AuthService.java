package com.example.desafio_picpay_simplificado.service;

import com.example.desafio_picpay_simplificado.dto.UserDTO;
import com.example.desafio_picpay_simplificado.exceptions.CredenciaisInvalidasException;
import com.example.desafio_picpay_simplificado.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public String login(UserDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);
            return jwtUtil.generateToken(data.email());
        } catch (AuthenticationException e){
            throw new CredenciaisInvalidasException("Credenciais inválidas.");
        }
    }
}