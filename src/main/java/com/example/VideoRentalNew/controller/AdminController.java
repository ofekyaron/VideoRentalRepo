package com.example.VideoRentalNew.controller;

import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.model.Order;
import com.example.VideoRentalNew.model.User;
import com.example.VideoRentalNew.service.MovieService;
import com.example.VideoRentalNew.service.OrderService;
import com.example.VideoRentalNew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) throws SQLException {
        model.addAttribute("totalMovies", movieService.getTotalMovies());
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("activeRentals", orderService.getActiveRentals());
        model.addAttribute("recentOrders", orderService.getRecentOrders(5));
        model.addAttribute("availableMovies", movieService.getAvailableMovies());
        return "admin/dashboard";
    }

    @GetMapping("/movies/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "admin/new-movie";
    }

    @GetMapping("/movies")
    public String listMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "admin/movies";  // Create a new Thymeleaf template for listing movies
    }

    @PostMapping("/movies/add")
    public String addMovie(@ModelAttribute Movie movie, RedirectAttributes redirectAttributes) throws SQLException {
        movieService.saveMovie(movie);
        redirectAttributes.addFlashAttribute("successMessage", "Movie added successfully!");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user, @RequestParam String role, RedirectAttributes redirectAttributes) {
        try {
            user.addRole(role);  // Add the selected role to the user
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "User '" + user.getUsername() + "' added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(id, user);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders";  // Create a new Thymeleaf template for listing movies
    }

    @PostMapping("/orders/{id}/return")
    public String returnMovie(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            orderService.returnMovie(id);
            redirectAttributes.addFlashAttribute("successMessage", "Movie returned successfully");
        } catch (IllegalStateException | SQLException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to return movie: " + e.getMessage());
        }
        return "redirect:/admin/orders";
    }

    // Add other admin-related methods here
}
