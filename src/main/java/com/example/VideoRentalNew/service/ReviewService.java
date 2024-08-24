package com.example.VideoRentalNew.service;

import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.model.Review;
import com.example.VideoRentalNew.model.User;
import com.example.VideoRentalNew.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsByMovieId(Integer movieId) {
        List<Review> reviews = reviewRepository.findByMovieIdWithUser(movieId);
        System.out.println("Raw reviews from repository: " + reviews);
        return reviews.stream()
                .filter(review -> review != null)
                .collect(Collectors.toList());
    }

    public Review saveReview(Review review, User user, Movie movie) {
        review.setUser(user);
        review.setMovie(movie);
        return reviewRepository.save(review);
    }
}