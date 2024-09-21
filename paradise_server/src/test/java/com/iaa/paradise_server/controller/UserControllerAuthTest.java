package com.iaa.paradise_server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import iaa.paradise.paradise_server.ParadiseApplication;
import iaa.paradise.paradise_server.models.User;
import iaa.paradise.paradise_server.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Optional;

@SpringBootTest(classes = ParadiseApplication.class)
@AutoConfigureMockMvc
public class UserControllerAuthTest {
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        objectMapper = new ObjectMapper();

        user = new User();
        user.setUsername("exampleUser");
        user.setPassword("examplePassword");
        user.setName("testName");
        user.setEmail("example@email.com");
        user.setPhone("1234567890");
        calendar.set(2000, Calendar.JANUARY, 1);
        user.setDob(calendar.getTime());
    }

    @Test
    void regsiterUserWithValidValues() {
        try {
            Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
            Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
            Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
            Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.message").value("User created successfully!"))
                    .andExpect(jsonPath("$.timeTaken").exists());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
