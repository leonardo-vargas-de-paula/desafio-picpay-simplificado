package com.example.desafio_picpay_simplificado;

import com.example.desafio_picpay_simplificado.controller.AuthController;
import com.example.desafio_picpay_simplificado.dto.UserDTO;
import com.example.desafio_picpay_simplificado.dto.UserDTOResponse;
import com.example.desafio_picpay_simplificado.model.user.UserType;
import com.example.desafio_picpay_simplificado.security.JwtUtil;
import com.example.desafio_picpay_simplificado.service.AuthService;
import com.example.desafio_picpay_simplificado.service.UserDetailService;
import com.example.desafio_picpay_simplificado.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserDetailService userDetailService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    public void mustCreateUser() throws Exception{
        UserDTO user1 = new UserDTO( "firstName", "lastName", "11111", BigDecimal.valueOf(10.1),"teste1@example.com", "senha1", UserType.COMMON);
        UserDTOResponse user1Response = new UserDTOResponse("firstName", "lastName","11111", BigDecimal.valueOf(10.1),"teste1@example.com",UserType.COMMON);

        Mockito.when(userService.createUser(user1)).thenReturn(user1Response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.email").value("teste1@example.com"))
                .andExpect(jsonPath("$.document").value("11111"))
                .andExpect(jsonPath("$.balance").value("10.1"))
                .andExpect(jsonPath("$.userType").value("COMMON"));

    }

}
