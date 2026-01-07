package com.example.desafio_picpay_simplificado;

import com.example.desafio_picpay_simplificado.controller.UserController;
import com.example.desafio_picpay_simplificado.dto.UserDTO;
import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.model.user.UserType;
import com.example.desafio_picpay_simplificado.service.UserService;
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
                .andExpect(jsonPath("$[1].email").value("teste2@example.com"))
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

}
