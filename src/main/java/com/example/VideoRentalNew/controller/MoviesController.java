package com.example.VideoRentalNew.controller;

import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    // You might want to add a method to handle movie details
    @GetMapping("/{id}")
    public String getMovieDetails(@PathVariable("id") Integer id, Model model) {
        try {
            Movie movie = movieService.getMovieById(id);
            if (movie != null) {
                model.addAttribute("movie", movie);
                return "movie-details";
            } else {
                model.addAttribute("error", "Movie not found.");
                return "error";
            }
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while fetching the movie details.");
            return "error";
        }
    }

    @Autowired
    public MoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String listMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movie-list";
    }

    @GetMapping("/search")
    public String searchMovies(
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "keywords", required = false) String keywords,
            Model model) {
        try {
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
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while searching for movies.");
            return "error";
        }
    }


}