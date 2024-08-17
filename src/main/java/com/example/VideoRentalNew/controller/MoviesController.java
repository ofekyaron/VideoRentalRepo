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

    @GetMapping
    public String listMovies(Model model) {
        try {
            List<Movie> movies = movieService.getAllMovies();
            model.addAttribute("movies", movies);
            return "movie-list";
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while fetching movies.");
            return "error";
        }
    }

    // Existing REST endpoints
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Movie>> getAllMovies() {
        try {
            List<Movie> movies = movieService.getAllMovies();
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... other existing REST endpoints ...

    // New methods for web interface
    @GetMapping("/browse")
    public String browseMovies(Model model) {
        try {
            List<Movie> movies = movieService.getAllMovies();
            model.addAttribute("movies", movies);
            return "browse";
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while fetching movies.");
            return "error";
        }
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam String query, Model model) {
        try {
            List<Movie> movies = movieService.searchMovies(query);
            model.addAttribute("movies", movies);
            model.addAttribute("searchQuery", query);
            return "search";
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while searching for movies.");
            return "error";
        }
    }

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
}