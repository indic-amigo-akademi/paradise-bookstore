package iaa.paradise.paradise_server.controllers;

import java.util.List;
import java.util.Map;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import iaa.paradise.paradise_server.enums.UserRole;
import iaa.paradise.paradise_server.models.User;
import iaa.paradise.paradise_server.service.PasswordService;
import iaa.paradise.paradise_server.service.UserService;
import iaa.paradise.paradise_server.utils.JsonResponse;
import iaa.paradise.paradise_server.utils.JsonResponseBuilder;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        try {
            user.setPassword(passwordService.hashPassword(user.getPassword()));
            User newUser = userService.saveUser(user);
            logger.info("User created: {}", newUser);

            return new ResponseEntity<>(jsonResponseBuilder
                    .setMessage("User created successfully")
                    .setSuccess(true).build().toJsonMap(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(jsonResponseBuilder
                    .setMessage(e.getMessage())
                    .setSuccess(false).build().toJsonMap(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("login")
    public User loginUser(@RequestBody String name, @RequestBody String password) {
        User user = userService.getUserByName(name);
        if (user != null && passwordService.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
