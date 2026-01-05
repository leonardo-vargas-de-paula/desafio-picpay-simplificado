package com.example.desafio_picpay_simplificado.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name= "users")
@Table(name= "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    String password;

    private String amount;
    @Enumerated(EnumType.STRING)
    private UserType userType;

}


