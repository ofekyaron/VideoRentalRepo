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
import org.springframework.stereotype.Indexed;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            return "order-history"; // Match the template name in your Thymeleaf folder
        } else {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }

    @GetMapping("/api/user/{userId}")
    @ResponseBody
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Integer userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Order> placeOrder(@RequestParam Integer userId, @RequestParam Integer movieId) throws SQLException {
        try {
            Order order = orderService.placeOrder(userId, movieId);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Order> getOrderDetails(Integer id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/return")
    @ResponseBody
    public ResponseEntity<Void> returnMovie(@PathVariable Integer id) {
        try {
            orderService.returnMovie(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalStateException | SQLException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/place")
    public String showPlaceOrderForm(@RequestParam Integer movieId, Model model) throws SQLException {
        Movie movie = movieService.getMovieById(movieId);
        if (movie != null && movie.isAvailable()) {
            model.addAttribute("movie", movie);
        }
        return "place-order";
    }
}
