package com.example.desafio_picpay_simplificado.model.user;

import com.example.desafio_picpay_simplificado.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String  document;

    @Column(unique = true)
    private String email;

    String password;

    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO userDTO) {
        this.firstName = userDTO.firstName();
        this.lastName=userDTO.lastName();
        this.document = userDTO.document();
        this.balance = userDTO.balance();
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.userType = userDTO.userType();
    }


}


