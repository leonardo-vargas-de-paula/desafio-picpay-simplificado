package com.example.desafio_picpay_simplificado;

import com.example.desafio_picpay_simplificado.controller.UserController;
import com.example.desafio_picpay_simplificado.dto.UserDTO;
import com.example.desafio_picpay_simplificado.dto.UserDTOResponse;
import com.example.desafio_picpay_simplificado.exceptions.RecursoNaoEncontradoException;
import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.model.user.UserType;
import com.example.desafio_picpay_simplificado.security.JwtAuthFilter;
import com.example.desafio_picpay_simplificado.security.JwtUtil;
import com.example.desafio_picpay_simplificado.security.config.SecurityConfig;
import com.example.desafio_picpay_simplificado.service.AuthService;
import com.example.desafio_picpay_simplificado.service.UserDetailService;
import com.example.desafio_picpay_simplificado.service.UserService;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    @MockitoBean
    private UserDetailService userDetailService;

    @MockitoBean
    private JwtUtil jwtUtil;


    @Test
    public void mustReturnAllUsers() throws Exception {
        List<UserDTO> usersDTO = new ArrayList<>();
        UserDTO user1 = new UserDTO( "firstName", "lastName", "11111", BigDecimal.valueOf(10.1),"teste1@example.com", "senha1", UserType.COMMON);
        UserDTO user2 = new UserDTO("firstName2", "lastName2", "22222", BigDecimal.valueOf(109830.1),"teste2@example.com", "senha2", UserType.MERCHANT);
        usersDTO.add(user1);
        usersDTO.add(user2);
        Mockito.when(userService.getAllUsers()).thenReturn(usersDTO);

        mockMvc.perform(get("/api/user")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("firstName"))
                .andExpect(jsonPath("$[0].email").value("teste1@example.com"))
                .andExpect(jsonPath("$[0].userType").value("COMMON"))
                .andExpect(jsonPath("$[1].firstName").value("firstName2"))
                .andExpect(jsonPath("$[1].userType").value("MERCHANT"));

    }

    @Test
    public void mustReturnUserById() throws Exception {
        User user1 = new User( 1L,"firstName", "lastName", "11111", "teste1@example.com", "senha1", BigDecimal.valueOf(10.1), UserType.COMMON);
        UserDTO userDTO = UserDTO.fromEntity(user1);

        Mockito.when(userService.getUserById(user1.getId())).thenReturn(userDTO);

        mockMvc.perform(get("/api/user/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.document").value("11111"))
                .andExpect(jsonPath("$.email").value("teste1@example.com"));

    }

    @Test
    public void mustDeleteUserById() throws Exception {
        User user1 = new User( 1L,"firstName", "lastName", "11111", "teste1@example.com", "senha1", BigDecimal.valueOf(10.1), UserType.COMMON);

        Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/user/{id}", user1.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void mustUpdateUser() throws Exception {
        User user1 = new User( 1L,"firstName", "lastName", "11111", "teste1@example.com", "senha1", BigDecimal.valueOf(10.1), UserType.COMMON);
        UserDTO dados = new UserDTO("firstName2", "lastName2", "11111", BigDecimal.valueOf(109830.1),"teste1@example.com", "senha1", UserType.COMMON);

        Mockito.when(userService.updateUser(1L, dados)).thenReturn(dados);

        mockMvc.perform(put("/api/user/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dados))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("firstName2"))
                .andExpect(jsonPath("$.lastName").value("lastName2"))
                .andExpect(jsonPath("$.email").value("teste1@example.com"))
                .andExpect(jsonPath("$.document").value("11111"))
                .andExpect(jsonPath("$.balance").value("109830.1"))
                .andExpect(jsonPath("$.password").value("senha1"))
                .andExpect(jsonPath("$.userType").value("COMMON"));


    }

    @Test
    public void mustReturnNotFoundWhenDeletingInvalidId() throws Exception {
        Long idInexistente = 99L;
        Mockito.doThrow(new RecursoNaoEncontradoException("Usuário não encontrado | Id: "+idInexistente)).when(userService).deleteUser(idInexistente);

        mockMvc.perform(delete("/api/user/{id}", idInexistente))
                .andExpect(status().isNotFound());
    }


}
