package com.iaa.paradise_server.Controllers;

import com.iaa.paradise_server.Entity.User;
import com.iaa.paradise_server.Repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Controller
public class MainController implements WebMvcConfigurer {
    @Autowired
    UserRepository usrdao;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index() {
        logger.info("Starting MainController.index");
        return "Hello from spring boot";
    }

    // @GetMapping("/auth/register")
    // public ModelAndView register() {
    // return new ModelAndView("register");
    // }

    @PostMapping(value = "/auth/register", produces = "application/json")
    @ResponseBody
    public String saveUser(@Valid User usr, BindingResult result) {
        logger.info("Starting MainController.saveUser");
        JSONObject res = new JSONObject();
        // logger.info(result.getAllErrors().toString());
        if (result.hasErrors()) {
            JSONObject errors = new JSONObject();
            result.getFieldErrors().forEach((err) -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });
            res.put("success", false);
            res.put("message", errors);
            return res.toString();
        }
        try {
            usr.setPassword(new BCryptPasswordEncoder().encode(usr.getPassword()));
            usrdao.save(usr);
            logger.info(usr.toString());
            res.put("success", true);
            res.put("message", "User registered successfully");
            return res.toString();
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", "Error while registering user: " + e.getMessage());
            return res.toString();
        }
    }

    @PostMapping(value = "/auth/login", produces = "application/json")
    @ResponseBody
    public String login(@RequestBody Map<String, String> body) {
        logger.info("Starting MainController.login");
        JSONObject res = new JSONObject();
        try {
            String username = body.get("username");
            String password = body.get("password");
            User usr = usrdao.findByUsername(username);
            if (usr == null) {
                res.put("success", false);
                res.put("message", "User not found");
                return res.toString();
            }
            if (!new BCryptPasswordEncoder().matches(password, usr.getPassword())) {
                res.put("success", false);
                res.put("message", "Invalid password");
                return res.toString();
            }
            res.put("success", true);
            res.put("message", "User logged in successfully");
            return res.toString();
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", "Error while logging in user: " + e.getMessage());
            return res.toString();
        }
    }

    @GetMapping("/getUsers")
    @ResponseBody
    public String getUsers(User usr) {
        logger.info("Starting MainController.getUsers");
        List<User> m = usrdao.findAll();
        String str = m.toString();
        // Object principal =
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // List<> m2=m.values().stream().map(s->
        // s.getUserName()).collect(Collectors.toList());
        System.out.println(str);
        return str;

    }

    // @GetMapping("/auth/login")
    // public ModelAndView loginPage() {
    // return new ModelAndView("login");
    // }

    @GetMapping("/auth/verify")
    public String verify() {
        logger.info("Starting MainController.verify");
        String user = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();
        return user;

    }

    @GetMapping("/home")
    public String home() {
        logger.info("Starting MainController.home");
        String user = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();
        return user;
    }

}