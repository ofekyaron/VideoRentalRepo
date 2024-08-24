package com.example.VideoRentalNew.service;

import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void addMovie(Movie movie) {
        validateMovie(movie);
        movieRepository.save(movie);
    }

    public List<Movie> getAvailableMovies() {
        return movieRepository.findByAvailableTrue();
    }

    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    public void updateMovie(Movie movie) {
        validateMovie(movie);
        movieRepository.save(movie);
    }

    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }

    private void validateMovie(Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Movie title cannot be null or empty");
        }
        // Additional validation can be added here
    }

    public List<Movie> searchMovies(String query) {
        return movieRepository.searchMovies(query);
    }

    public List<Movie> searchMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> searchMoviesByKeywords(String keywords) {
        return movieRepository.findByTitleContainingIgnoreCase(keywords);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> searchMovies(String genre, String keywords) {
        if ((genre == null || genre.isEmpty()) && (keywords == null || keywords.isEmpty())) {
            return getAllMovies();
        }
        return movieRepository.findAll().stream()
                .filter(movie -> (genre == null || genre.isEmpty() || movie.getGenre().toLowerCase().contains(genre.toLowerCase())))
                .filter(movie -> (keywords == null || keywords.isEmpty() || movie.getTitle().toLowerCase().contains(keywords.toLowerCase())))
                .collect(Collectors.toList());
    }

    public long getTotalMovies() {
        return movieRepository.count();
    }

    public List<Movie> getAvailableMovies(int limit) {
        return movieRepository.findByAvailableTrueOrderByIdDesc(limit);
    }

    public Set<String> getUniqueGenres() {
        return movieRepository.findAll().stream()
                .map(Movie::getGenre)
                .collect(Collectors.toSet());
    }

    public void saveMovie(Movie movie) {
        validateMovie(movie);
        movieRepository.save(movie);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }
}