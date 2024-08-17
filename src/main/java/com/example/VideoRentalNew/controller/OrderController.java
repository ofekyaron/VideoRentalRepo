package com.example.VideoRentalNew.controller;

import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.model.Order;
import com.example.VideoRentalNew.model.User;
import com.example.VideoRentalNew.service.OrderService;
import com.example.VideoRentalNew.service.UserService;
import com.example.VideoRentalNew.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final MovieService movieService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, MovieService movieService) {
        this.orderService = orderService;
        this.userService = userService;
        this.movieService = movieService;
    }

    @GetMapping
    public String listOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        if (user != null) {
            List<Order> orders = orderService.getOrdersByUser(user.getId());
            model.addAttribute("orders", orders);
            return "order-history"; // Ensure this Thymeleaf template exists
        } else {
            model.addAttribute("error", "User not found");
            return "error"; // Ensure this Thymeleaf template exists
        }
    }

    @GetMapping("/api/user/{userId}")
    @ResponseBody
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Integer userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public String placeOrder(@RequestParam Integer userId, @RequestParam Integer movieId, Model model) {
        try {
            Order order = orderService.placeOrder(userId, movieId);
            model.addAttribute("order", order);
            return "order-success"; // Ensure this Thymeleaf template exists
        } catch (RuntimeException e) {
            model.addAttribute("error", "Unable to place order");
            return "order-error"; // Ensure this Thymeleaf template exists
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Order> getOrderDetails(@PathVariable Integer id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/return")
    public String returnMovie(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            orderService.returnMovie(id);
            redirectAttributes.addFlashAttribute("message", "Return succeeded");
            return "redirect:/orders/confirmation";
        } catch (IllegalStateException | SQLException e) {
            redirectAttributes.addFlashAttribute("error", "Unable to return movie");
            return "redirect:/orders";
        }
    }


    @GetMapping("/place")
    public String showPlaceOrderForm(@RequestParam Integer movieId, Model model) throws SQLException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);

        if (user != null) {
            Movie movie = movieService.getMovieById(movieId);
            if (movie != null && movie.isAvailable()) {
                model.addAttribute("movie", movie);
                model.addAttribute("currentUser", user);
                return "place-order"; // Ensure this Thymeleaf template exists
            }
        }
        model.addAttribute("error", "Movie not available or user not found");
        return "error"; // Ensure this Thymeleaf template exists
    }

    @PostMapping("/place")
    public String handlePlaceOrder(@RequestParam Integer movieId, Model model) throws SQLException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        if (user != null) {
            try {
                Order order = orderService.placeOrder(user.getId(), movieId);
                model.addAttribute("order", order);
                return "redirect:/orders/confirmation"; // Ensure this redirect URL is handled properly
            } catch (RuntimeException e) {
                model.addAttribute("error", "Failed to place the order");
                return "place-order"; // Ensure this Thymeleaf template exists
            }
        } else {
            model.addAttribute("error", "User not found");
            return "error"; // Ensure this Thymeleaf template exists
        }
    }

    @GetMapping("/confirmation")
    public String showOrderConfirmation(Model model) {
        // Add any additional logic needed for the confirmation page
        return "order-confirmation"; // Ensure this Thymeleaf template exists
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam(required = false) String genre,
                               @RequestParam(required = false) String keywords,
                               Model model) {
        List<Movie> movies;
        if (genre != null && !genre.isEmpty()) {
            movies = movieService.searchMoviesByGenre(genre);
        } else if (keywords != null && !keywords.isEmpty()) {
            movies = movieService.searchMoviesByKeywords(keywords);
        } else {
            movies = movieService.findAll(); // Default to show all movies
        }
        model.addAttribute("movies", movies);
        return "movie-list"; // Your Thymeleaf template
    }
}
