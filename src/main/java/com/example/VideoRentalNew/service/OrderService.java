package com.example.VideoRentalNew.service;

import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.model.Order;
import com.example.VideoRentalNew.model.User;
import com.example.VideoRentalNew.repository.OrderRepository;
import com.example.VideoRentalNew.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final MovieService movieService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(MovieService movieService, OrderRepository orderRepository, UserRepository userRepository) {
        this.movieService = movieService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public Order placeOrder(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Movie movie = movieService.getMovieById(movieId);

        if (movie == null) {
            throw new IllegalStateException("Movie not found");
        }

        if (!movie.isAvailable()) {
            throw new IllegalStateException("Movie is not available for rent");
        }

        Order order = new Order(userId, movieId, LocalDateTime.now());
        order = orderRepository.save(order);

        movie.setAvailable(false);
        movieService.updateMovie(movie);

        return order;
    }

    @Transactional
    public void returnMovie(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found"));

        if (order.getReturnDate() != null) {
            throw new IllegalStateException("Movie already returned");
        }

        order.setReturnDate(LocalDateTime.now());
        orderRepository.save(order);

        Movie movie = movieService.getMovieById(order.getMovieId());
        if (movie != null) {
            movie.setAvailable(true);
            movieService.updateMovie(movie);
        }
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Order not found"));
    }

    public List<Order> getRecentOrdersByUser(Integer userId) {
        return orderRepository.findRecentOrdersByUser(5); // Get last 5 orders
    }

    public long getActiveRentals() {
        return orderRepository.countByReturnDateIsNull();
    }

    public List<Order> getRecentOrders(int limit) {
        return orderRepository.findAllOrderByOrderDateDesc().stream()
                .limit(limit)
                .toList();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}