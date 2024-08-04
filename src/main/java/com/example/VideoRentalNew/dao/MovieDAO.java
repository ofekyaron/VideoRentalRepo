package com.example.VideoRentalNew.dao;

import com.example.VideoRentalNew.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    private final String url = "jdbc:sqlite:src/main/resources/db/video_rental.db";

    // Method to update a movie
    public void updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE movies SET title = ?, genre = ?, releaseYear = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getGenre());
            pstmt.setInt(3, movie.getReleaseYear());
            pstmt.setString(4, movie.getDirector());
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
        String sql = "INSERT INTO movies (title, genre, releaseYear) VALUES (?, ?, ?)";

        // Establish a connection to the database
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDirector());
            pstmt.setString(3, movie.getGenre());
            pstmt.setInt(4, movie.getReleaseYear());

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
                    movie.setDirector(rs.getString("director"));
                    movie.setTitle(rs.getString("title"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setReleaseYear(rs.getInt("releaseYear"));
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
                movie.setDirector(rs.getString("director"));
                movie.setTitle(rs.getString("title"));
                movie.setGenre(rs.getString("genre"));
                movie.setReleaseYear(rs.getInt("releaseYear"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return movies;
    }



}
