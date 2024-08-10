package com.example.VideoRentalNew.dao;

import com.example.VideoRentalNew.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    private final String url = "jdbc:sqlite:src/main/resources/db/video_rental.db";

    // Method to update a movie
    public void updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE movies SET title = ?, genre = ?, release_year = ?, description = ?, available = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getGenre());
            pstmt.setInt(3, movie.getReleaseYear());
            pstmt.setString(4, movie.getDescription());
            pstmt.setBoolean(5, movie.isAvailable());
            pstmt.setLong(6, movie.getId());
            pstmt.executeUpdate();
        }
    }

    // Method to delete a movie by ID
    public void deleteMovie(int id) throws SQLException {
        String sql = "DELETE FROM movies WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Method to add a new movie
    public void addMovie(Movie movie) throws SQLException {
        String sql = "INSERT INTO movies (title, genre, release_year, description, available) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getGenre());
            pstmt.setInt(3, movie.getReleaseYear());
            pstmt.setString(4, movie.getDescription());
            pstmt.setBoolean(5, movie.isAvailable());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Method to get a movie by ID
    public Movie getMovieById(int id) throws SQLException {
        Movie movie = null;
        String sql = "SELECT * FROM movies WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    movie = new Movie();
                    movie.setId(rs.getLong("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setReleaseYear(rs.getInt("release_year"));
                    movie.setDescription(rs.getString("description"));
                    movie.setAvailable(rs.getBoolean("available"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return movie;
    }

    // Method to get all movies
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getLong("id"));
                movie.setTitle(rs.getString("title"));
                movie.setGenre(rs.getString("genre"));
                movie.setReleaseYear(rs.getInt("release_year"));
                movie.setDescription(rs.getString("description"));
                movie.setAvailable(rs.getBoolean("available"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return movies;
    }

    public List<Movie> getAvailableMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE available = TRUE";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getLong("id"));
                movie.setTitle(rs.getString("title"));
                movie.setGenre(rs.getString("genre"));
                movie.setReleaseYear(rs.getInt("release_year"));
                movie.setDescription(rs.getString("description"));
                movie.setAvailable(rs.getBoolean("available"));
                movies.add(movie);
            }
        }

        return movies;
    }

    // Update other methods to use Long instead of int for IDs
    public Movie getMovieById(Long id) throws SQLException {
        Movie movie = null;
        String sql = "SELECT * FROM movies WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    movie = new Movie();
                    movie.setId(rs.getLong("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setReleaseYear(rs.getInt("release_year"));
                    movie.setDescription(rs.getString("description"));
                    movie.setAvailable(rs.getBoolean("available"));
                }
            }
        }

        return movie;
    }

    public void deleteMovie(Long id) throws SQLException {
        String sql = "DELETE FROM movies WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

}