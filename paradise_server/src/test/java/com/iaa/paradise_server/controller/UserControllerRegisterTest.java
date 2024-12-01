package com.iaa.paradise_server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import java.util.Date;
import java.util.Optional;

@SpringBootTest(classes = ParadiseApplication.class)
@AutoConfigureMockMvc
public class UserControllerRegisterTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private Calendar calendar;
    private User user;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        calendar = Calendar.getInstance();
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

    @Test
    void registerUserWithUsernameTaken() {
        try {
            Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.username[0]").value("Username already taken!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerUserWithBlankUsername() {
        try {
            user.setUsername(null);
            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.username[0]").value("Username is required!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerUserWithInvalidUsername() {
        try {
            user.setUsername("invalidUsernameWithMoreThan20Characters");
            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.username[0]").value("Username must be between 3 and 20 characters!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerUserWithEmailTaken() {
        try {
            Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.email[0]").value("Email is already in use!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Test
    // void registerUserWithBlankEmail() {
    //     try {
    //         user.setEmail(null);
    //         ResultActions resultActions = mockMvc.perform(
    //                 post("/auth/register")
    //                         .contentType(MediaType.APPLICATION_JSON)
    //                         .content(objectMapper.writeValueAsString(user)));

    //         resultActions
    //                 .andExpect(status().isBadRequest())
    //                 .andExpect(jsonPath("$.success").value(false))
    //                 .andExpect(jsonPath("$.message").value("Validation failed!"))
    //                 .andExpect(jsonPath("$.errors.email[0]").value("Email is required!"))
    //                 .andExpect(jsonPath("$.timeTaken").exists());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    @Test
    void registerUserWithInvalidEmail() {
        try {
            user.setEmail("invalidEmail");
            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.email[0]").value("Email must be valid!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerUserWithPhoneTaken() {
        try {
            Mockito.when(userRepository.findByPhone(user.getPhone())).thenReturn(Optional.of(user));

            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.phone[0]").value("Phone is already in use!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Test
    // void registerUserWithBlankPhone() {
    //     try {
    //         user.setPhone(null);
    //         ResultActions resultActions = mockMvc.perform(
    //                 post("/auth/register")
    //                         .contentType(MediaType.APPLICATION_JSON)
    //                         .content(objectMapper.writeValueAsString(user)));

    //         resultActions
    //                 .andExpect(status().isBadRequest())
    //                 .andExpect(jsonPath("$.success").value(false))
    //                 .andExpect(jsonPath("$.message").value("Validation failed!"))
    //                 .andExpect(jsonPath("$.errors.phone[0]").value("Phone is required!"))
    //                 .andExpect(jsonPath("$.timeTaken").exists());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    @Test
    void registerUserWithPhoneTooShort() {
        try {
            user.setPhone("1234567");
            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.phone[0]").value("Phone must be 10 digits long!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerUserWithInvalidPhone() {
        try {
            user.setPhone("invalidPhone");
            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.phone[0]").value("Phone must be numeric!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerUserWithBlankPassword() {
        try {
            user.setPassword(null);
            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.password[0]").value("Password is required!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerUserWithPasswordTooShort() {
        try {
            user.setPassword("123");
            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.password[0]").value("Password must be at least 8 characters!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerUserWithNullDob() {
        try {
            user.setDob(null);
            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.dob[0]").value("Date of birth is required!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerUserWithInvalidDob() {
        try {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 3);
            user.setDob(calendar.getTime());
            ResultActions resultActions = mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)));

            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Validation failed!"))
                    .andExpect(jsonPath("$.errors.dob[0]").value("Date of birth should be a past date!"))
                    .andExpect(jsonPath("$.timeTaken").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
