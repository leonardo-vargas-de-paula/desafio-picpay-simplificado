package com.example.desafio_picpay_simplificado.controller;

import com.example.desafio_picpay_simplificado.dto.UserDTO;
import com.example.desafio_picpay_simplificado.dto.UserDTOResponse;
import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.security.JwtUtil;
import com.example.desafio_picpay_simplificado.service.AuthService;
import com.example.desafio_picpay_simplificado.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTOResponse> register(@RequestBody UserDTO userDTO) throws Exception{
        UserDTOResponse userDTOResponse = userService.createUser(userDTO);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.CREATED);

    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        String token = authService.login(userDTO);
        return ResponseEntity.ok(Map.of("token", token));

    }


}
