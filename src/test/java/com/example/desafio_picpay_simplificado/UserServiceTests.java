package com.example.desafio_picpay_simplificado;

import com.example.desafio_picpay_simplificado.dto.UserDTO;
import com.example.desafio_picpay_simplificado.dto.UserDTOResponse;
import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.model.user.UserType;
import com.example.desafio_picpay_simplificado.repository.UserRepository;
import com.example.desafio_picpay_simplificado.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;

    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO(
                "João", "Silva", "12345678900", new BigDecimal("100.00"),
                "joao@email.com", "senha123", UserType.COMMON
        );

        user = new User(userDTO);
        user.setId(1L);
    }

    @Test
    void createUserSuccess() throws Exception {
        when(userRepository.findUserByEmail(userDTO.email())).thenReturn(Optional.empty());
        when(userRepository.findUserByDocument(userDTO.document())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDTO.password())).thenReturn("encoded_pass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTOResponse response = userService.createUser(userDTO);

        assertThat(response).isNotNull();
        assertThat(response.firstName()).isEqualTo(userDTO.firstName());
        assertThat(response.lastName()).isEqualTo(userDTO.lastName());
        assertThat(response.document()).isEqualTo(userDTO.document());
        assertThat(response.balance()).isEqualTo(userDTO.balance());
        assertThat(response.email()).isEqualTo(userDTO.email());
        assertThat(response.userType()).isEqualTo(userDTO.userType());


        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(userDTO.password());
    }


}
