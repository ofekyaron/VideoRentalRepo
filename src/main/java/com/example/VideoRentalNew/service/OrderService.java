package com.example.VideoRentalNew.service;

import com.example.VideoRentalNew.dao.OrderDAO;
import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.model.Order;
import com.example.VideoRentalNew.model.User;
import com.example.VideoRentalNew.repository.OrderRepository;
import com.example.VideoRentalNew.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderDAO orderDAO;
    private final MovieService movieService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderDAO orderDAO, MovieService movieService,
                        OrderRepository orderRepository, UserRepository userRepository) {
        this.orderDAO = orderDAO;
        this.movieService = movieService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public Order placeOrder(Integer userId, Integer movieId) throws IllegalStateException, SQLException {
        User user = userRepository.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Movie movie = movieService.getMovieById(movieId);

        if (movie == null) {
            throw new IllegalStateException("Movie not found");
        }

        if (!movie.isAvailable()) {
            throw new IllegalStateException("Movie is not available for rent");
        }

        Order order = new Order(userId, movieId, LocalDateTime.now());
        orderDAO.create(order);

        movie.setAvailable(false);
        movieService.updateMovie(movie);

        return order;
    }

    @Transactional
    public void returnMovie(Integer orderId) throws IllegalStateException, SQLException {
        Order order = orderDAO.find(orderId);
        if (order == null) {
            throw new IllegalStateException("Order not found");
        }
        if (order.getReturnDate() != null) {
            throw new IllegalStateException("Movie already returned");
        }

        order.setReturnDate(LocalDateTime.now());
        orderDAO.update(order);

        Movie movie = movieService.getMovieById(order.getMovieId());
        if (movie != null) {
            movie.setAvailable(true);
            movieService.updateMovie(movie);
        }
    }

    public Order getOrderById(Integer id) {
        return orderDAO.find(id);
    }

    public List<Order> getRecentOrdersByUser(Integer userId) {
        try {
            return orderDAO.getRecentOrdersByUser(userId, 5); // Get last 5 orders
        } catch (SQLException e) {
            // Log the error
            e.printStackTrace();
            return List.of(); // Return an empty list in case of error
        }
    }

    public long getActiveRentals() {
        return orderRepository.countByReturnDateIsNull();
    }

    public List<Order> getRecentOrders(int limit) {
        return orderRepository.findTopNOrderByOrderDateDesc(limit);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
