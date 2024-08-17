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

//    @PostMapping("/movies/add")
//    public String addMovie(@ModelAttribute Movie movie) throws SQLException {
//        movieService.saveMovie(movie);
//        return "redirect:/admin/dashboard";  // Redirect to dashboard instead of /admin/movies
//    }

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
        return "admin/users";  // Create a new Thymeleaf template for listing movies
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders";  // Create a new Thymeleaf template for listing movies
    }

    // Add other admin-related methods here
}
