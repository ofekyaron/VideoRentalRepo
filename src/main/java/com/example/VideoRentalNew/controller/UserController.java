package com.example.VideoRentalNew.controller;

import com.example.VideoRentalNew.model.User;
import com.example.VideoRentalNew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email) throws SQLException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Password will be encoded in UserService
        user.setEmail(email);
        user.setRoles("USER"); // Set default role
        userService.registerUser(user);
        return "redirect:/login";
    }

    // Other controller methods
}