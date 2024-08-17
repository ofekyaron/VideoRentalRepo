package com.example.VideoRentalNew.bean;

import com.example.VideoRentalNew.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
public class UserBean implements Serializable {
    private User currentUser;

    public String logout() {
        // Implement logout logic
        currentUser = null;
        return "redirect:/login";
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}