package com.example.VideoRentalNew.dao;

import com.example.VideoRentalNew.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    private final String url = "jdbc:sqlite:src/main/resources/db/video_rental.db";

    // Method to update a movie
    public void updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE movies SET title = ?, genre = ?, release_year = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, movie.getDescription());
            pstmt.setInt(2, movie.getReleaseYear());
            pstmt.setString(3, movie.getGenre());
            pstmt.setString(4, movie.getTitle());
            pstmt.setInt(5, movie.getId());
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

    public void addMovie(Movie movie) throws SQLException {
        String sql = "INSERT INTO movies (title, genre, release_year) VALUES (?, ?, ?)";

        // Establish a connection to the database
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(3, movie.getGenre());
            pstmt.setInt(4, movie.getReleaseYear());
            pstmt.setString(5, movie.getDescription());

            // Execute the update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            throw e; // Re-throw the exception if needed
        }
    }

    public Movie getMovieById(int id) throws SQLException {
        Movie movie = null;
        String sql = "SELECT * FROM movies WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    movie = new Movie();
                    movie.setId((long) rs.getInt("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setReleaseYear(rs.getInt("release_year"));
                    movie.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return movie;
    }

    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId((long) rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setGenre(rs.getString("genre"));
                movie.setReleaseYear(rs.getInt("release_year"));
                movie.setDescription(rs.getString("description"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return movies;
    }



}
