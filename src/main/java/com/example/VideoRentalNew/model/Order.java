package com.example.VideoRentalNew.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "user_id", nullable = false) // Maps user_id to User entity
    private Integer userId;

    @JoinColumn(name = "movie_id", nullable = false) // Maps movie_id to Movie entity
    private Integer movieId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    // Constructors
    public Order() {}

    public Order(Integer userId, Integer movieId, LocalDateTime orderDate) {
        this.userId = userId;
        this.movieId = movieId;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return userId;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovie(Integer movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }




    // You might want to add toString(), equals(), and hashCode() methods
}