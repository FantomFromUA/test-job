package com.clearsolution.testjob.controller;

import com.clearsolution.testjob.DTO.UserDTO;
import com.clearsolution.testjob.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.clearsolution.testjob.Controller.UserController;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    private UserDTO userDTO;

    @BeforeEach
    public void init() {
        userDTO = UserDTO
                .builder()
                .firstName("Erik")
                .lastName("EEEE")
                .email("erik@mail.com")
                .birthDate(LocalDateTime.of(2003, 9, 3, 0, 0, 0))
                .address("street")
                .phoneNumber("123456789")
                .build();
    }

    @Test
    public void userPostTest() throws Exception {

        when(userService.postUser(Mockito.any(UserDTO.class))).thenReturn(userDTO);

        ResultActions response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void userGetByIdTest() throws Exception {

        when(userService.getUserById(1)).thenReturn(userDTO);

        ResultActions response = mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(userDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(userDTO.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userDTO.getEmail())));
    }

}
