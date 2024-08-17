package com.example.VideoRentalNew.service;

import com.example.VideoRentalNew.dao.MovieDAO;
import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieDAO movieDAO;
    private final MovieRepository movieRepository;


    @Autowired
    public MovieService(MovieDAO movieDAO, MovieRepository movieRepository) {
        this.movieDAO = movieDAO;
        this.movieRepository = movieRepository;
    }

    // Method to add a new movie
    public void addMovie(Movie movie) throws SQLException {
        // Validate movie data if necessary
        validateMovie(movie);

        // Add movie using DAO
        movieDAO.addMovie(movie);
    }

    public List<Movie> getAvailableMovies() throws SQLException {
        return movieDAO.getAvailableMovies();
    }

    // Method to get a movie by IDdeleteMovie
    public Movie getMovieById(Integer id) throws SQLException {
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
    public void deleteMovie(Integer id) throws SQLException {
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

    public List<Movie> searchMovies(String query) throws SQLException {
        // Implement search logic here
        // This is a basic example; you might want to make this more sophisticated
        return movieDAO.searchMovies(query);
    }

    public List<Movie> searchMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> searchMoviesByKeywords(String keywords) {
        return movieRepository.findByTitleContainingIgnoreCase(keywords);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> searchMovies(String genre, String keywords) {
        List<Movie> movies = movieRepository.findAll();
        if (genre != null && !genre.isEmpty()) {
            movies = movies.stream()
                    .filter(movie -> movie.getGenre().toLowerCase().contains(genre.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (keywords != null && !keywords.isEmpty()) {
            movies = movies.stream()
                    .filter(movie -> movie.getTitle().toLowerCase().contains(keywords.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return movies;
    }

    public void saveMovie(Movie movie) throws SQLException {
        movieRepository.save(movie);
    }

    public long getTotalMovies() {
        return movieRepository.count();
    }

    public List<Movie> getAvailableMovies(int limit) {
        return movieRepository.findByAvailableTrueOrderByIdDesc(limit);
    }

}

