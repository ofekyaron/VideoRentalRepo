package com.example.VideoRentalNew.service;

import com.example.VideoRentalNew.dao.MovieDAO;
import com.example.VideoRentalNew.model.Movie;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MovieService {

    private MovieDAO movieDAO;

    // Constructor to initialize the DAO
    public MovieService() {
        this.movieDAO = new MovieDAO(); // Initialize DAO
    }

    // Method to add a new movie
    public void addMovie(Movie movie) throws SQLException {
        // Validate movie data if necessary
        validateMovie(movie);

        // Add movie using DAO
        movieDAO.addMovie(movie);
    }

    // Method to get all movies
    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    public List<Movie> getAvailableMovies() throws SQLException {
        return movieDAO.getAvailableMovies();
    }

    // Method to get a movie by IDdeleteMovie
    public Movie getMovieById(Long id) throws SQLException {
        return movieDAO.getMovieById(id);
    }

    // Method to update an existing movie
    public void updateMovie(Movie movie) throws SQLException {
        // Validate movie data if necessary
        validateMovie(movie);

        // Update movie using DAO
        movieDAO.updateMovie(movie);
    }

    // Method to delete a movie by ID
    public void deleteMovie(Long id) throws SQLException {
        // Delete movie using DAO
        movieDAO.deleteMovie(id);
    }

    // Helper method to validate movie data
    private void validateMovie(Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Movie title cannot be null or empty");
        }
        // Additional validation can be added here
    }
}
