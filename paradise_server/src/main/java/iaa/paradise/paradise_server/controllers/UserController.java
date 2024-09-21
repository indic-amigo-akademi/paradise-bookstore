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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iaa.paradise.paradise_server.enums.UserRole;
import iaa.paradise.paradise_server.models.User;
import iaa.paradise.paradise_server.service.SequenceGeneratorService;
import iaa.paradise.paradise_server.service.UserService;
import iaa.paradise.paradise_server.utils.JsonResponse;
import iaa.paradise.paradise_server.utils.JsonResponseBuilder;
import iaa.paradise.paradise_server.utils.RequestTimeInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@PropertySource("classpath:message.properties")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("register")
    public ResponseEntity<JsonResponse> registerUser(@Valid @RequestBody User user,
            BindingResult bindingResult, HttpServletRequest request) {
        Map<String, List<String>> errors = new HashMap<>();
        try {
            bindingResult.getFieldErrors()
                    .forEach(error -> errors.computeIfAbsent(error.getField(), k -> new ArrayList<>())
                            .add(error.getDefaultMessage()));

            if (!errors.isEmpty()) {
                return new ResponseEntity<>(jsonResponseBuilder
                        .setMessage(env.getProperty("valid.failed"))
                        .setSuccess(false)
                        .setErrors(errors)
                        .updateTimeTaken(request)
                        .build(), HttpStatus.BAD_REQUEST);
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
                    .setMessage(env.getProperty("user.registered.success"))
                    .updateTimeTaken(request)
                    .setSuccess(true).build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(jsonResponseBuilder
                    .setMessage(e.getMessage())
                    .updateTimeTaken(request)
                    .setSuccess(false).build(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("login")
    public ResponseEntity<JsonResponse> loginUser(@RequestBody String username, @RequestBody String password, HttpServletRequest request) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            // Generate JWT token

            return new ResponseEntity<>(jsonResponseBuilder
                    .setMessage("User logged in successfully")
                    .updateTimeTaken(request)
                    .setSuccess(true).build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(jsonResponseBuilder
                .setMessage("Invalid username or password")
                .updateTimeTaken(request)
                .setSuccess(false).build(), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("logout")
    public JsonResponse logoutUser() {
        return new JsonResponse();
    }
}
