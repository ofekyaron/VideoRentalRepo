package com.example.VideoRentalNew.repository;

import com.example.VideoRentalNew.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.user WHERE r.movie.id = :movieId")
    List<Review> findByMovieIdWithUser(@Param("movieId") Integer movieId);
}