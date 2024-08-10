package com.example.VideoRentalNew.controller;

import com.example.VideoRentalNew.model.Order;
import com.example.VideoRentalNew.model.User;
import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.service.OrderService;
import com.example.VideoRentalNew.service.UserService;
import com.example.VideoRentalNew.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
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

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private MovieService movieService;
//
//    private final OrderService orderService;
//
//    @Autowired
//    public OrderController(OrderService orderService) {
//        this.orderService = orderService;
//    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestParam Long userId, @RequestParam Long movieId) throws SQLException {
        try {
            User user = userService.getUserById(userId);
            Movie movie = movieService.getMovieById(movieId);
            Order order = orderService.placeOrder(user, movie);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnMovie(@PathVariable Long id) {
        try {
            orderService.returnMovie(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalStateException | SQLException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
