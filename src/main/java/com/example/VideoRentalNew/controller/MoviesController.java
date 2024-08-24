package com.example.VideoRentalNew.controller;

import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.model.Review;
import com.example.VideoRentalNew.model.User;
import com.example.VideoRentalNew.service.MovieService;
import com.example.VideoRentalNew.service.ReviewService;
import com.example.VideoRentalNew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/movies")
public class MoviesController {

    private MovieService movieService;
    private ReviewService reviewService;
    private UserService userService;

    @Autowired
    public void MovieController(MovieService movieService, ReviewService reviewService, UserService userService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping("/{id}/review")
    public String addReview(@PathVariable Integer id, @ModelAttribute("newReview") Review review,
                            @AuthenticationPrincipal UserDetails userDetails,
                            RedirectAttributes redirectAttributes) {
        try {
            Movie movie = movieService.getMovieById(id);
            User user = userService.findByUsername(userDetails.getUsername());
            reviewService.saveReview(review, user, movie);
            redirectAttributes.addFlashAttribute("successMessage", "Review added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding review: " + e.getMessage());
        }
        return "redirect:/movies/" + id;
    }


    @GetMapping("/{id}")
    public String getMovieDetails(@PathVariable Integer id, Model model) throws SQLException {
        Movie movie = movieService.getMovieById(id);
        System.out.println("Movie fetched: " + movie);
        if (movie == null) {
            return "redirect:/movies";
        }

        List<Review> reviews = reviewService.getReviewsByMovieId(id);
        System.out.println("Reviews fetched: " + reviews);

        model.addAttribute("movie", movie);
        model.addAttribute("reviews", reviews);
        model.addAttribute("newReview", new Review());
        return "movie-details";
    }

    @Autowired
    public MoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String listMovies(Model model,
                             @RequestParam(required = false) String genre,
                             @RequestParam(required = false) String keywords) {
        List<Movie> movies;
        if (genre != null && !genre.isEmpty()) {
            movies = movieService.getMoviesByGenre(genre);
        } else if (keywords != null && !keywords.isEmpty()) {
            movies = movieService.searchMoviesByKeywords(keywords);
        } else {
            movies = movieService.getAllMovies();
        }

        Set<String> genres = movieService.getUniqueGenres();

        model.addAttribute("movies", movies);
        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", genre);
        return "movie-list";
    }

    @GetMapping("/search")
    public String searchMovies(
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "keywords", required = false) String keywords,
            Model model) {
        List<Movie> movies;
        if (genre != null && keywords != null) {
            movies = movieService.searchMovies(genre, keywords);
        } else if (keywords != null) {
            movies = movieService.searchMovies(keywords);
        } else {
            movies = movieService.getAllMovies(); // Or handle case where no parameters are provided
        }
        model.addAttribute("movies", movies);
        model.addAttribute("genre", genre);
        model.addAttribute("keywords", keywords);
        return "movie-list";
    }


}