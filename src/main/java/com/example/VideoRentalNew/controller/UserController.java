package com.example.VideoRentalNew.controller;

import com.example.VideoRentalNew.model.User;
import com.example.VideoRentalNew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email) throws SQLException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Ensure this is BCrypt encoded
        user.setEmail(email);
        user.setRoles("USER"); // Store role without 'ROLE_' prefix
        return "redirect:/login";
    }

    // Other controller methods
}
