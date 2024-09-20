package iaa.paradise.paradise_server.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import iaa.paradise.paradise_server.enums.UserRole;
import iaa.paradise.paradise_server.models.User;
import iaa.paradise.paradise_server.service.SequenceGeneratorService;
import iaa.paradise.paradise_server.service.UserService;
import iaa.paradise.paradise_server.utils.JsonResponse;
import iaa.paradise.paradise_server.utils.JsonResponseBuilder;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody User user,
            BindingResult bindingResult, HttpServletResponse response) {
        Map<String, List<String>> errors = new HashMap<>();
        try {
            bindingResult.getFieldErrors()
                    .forEach(error -> errors.computeIfAbsent(error.getField(), k -> new ArrayList<>())
                            .add(error.getDefaultMessage()));

            if (userService.getUserByUsername(user.getUsername()).isPresent()) {
                errors.computeIfAbsent("username", k -> new ArrayList<>()).add("Username is already registered.");
            }
            if (userService.getUserByEmail(user.getEmail()).isPresent()) {
                errors.computeIfAbsent("email", k -> new ArrayList<>()).add("Email is already registered.");
            }

            if (!errors.isEmpty()) {
                return new ResponseEntity<>(jsonResponseBuilder
                        .setMessage("Validation failed!")
                        .setSuccess(false)
                        .setErrors(errors)
                        .setTimeTaken(Long.parseLong(response.getHeader("X-Execution-Time") == null ? "0"
                                : response.getHeader("X-Execution-Time")))
                        .build().toJsonMap(), HttpStatus.BAD_REQUEST);
            }

            // Hash the password before saving
            user
                    .setPassword(passwordEncoder.encode(user.getPassword()))
                    .setRole(UserRole.USER)
                    .setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME))
                    .setCreatedTs(new Date())
                    .setUpdatedTs(new Date());

            User newUser = userService.saveUser(user);
            logger.info("User created: {}", newUser);

            return new ResponseEntity<>(jsonResponseBuilder
                    .setMessage("User created successfully")
                    .setTimeTaken(Long.parseLong(response.getHeader("X-Execution-Time") == null ? "0"
                            : response.getHeader("X-Execution-Time")))
                    .setSuccess(true).build().toJsonMap(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(jsonResponseBuilder
                    .setMessage(e.getMessage())
                    .setTimeTaken(Long.parseLong(response.getHeader("X-Execution-Time") == null ? "0"
                            : response.getHeader("X-Execution-Time")))
                    .setSuccess(false).build().toJsonMap(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("login")
    public User loginUser(@RequestBody String username, @RequestBody String password) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        }
        return null;
    }

    @PostMapping("logout")
    public JsonResponse logoutUser() {
        return new JsonResponse();
    }
}
