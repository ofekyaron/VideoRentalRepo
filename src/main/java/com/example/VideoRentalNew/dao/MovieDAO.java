package com.example.VideoRentalNew.dao;

import com.example.VideoRentalNew.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MovieDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Movie> movieRowMapper = (ResultSet rs, int rowNum) -> {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setTitle(rs.getString("title"));
        movie.setGenre(rs.getString("genre"));
        movie.setReleaseYear(rs.getInt("release_year"));
        movie.setDescription(rs.getString("description"));
        movie.setAvailable(rs.getBoolean("available"));
        return movie;
    };

    public void updateMovie(Movie movie) {
        String sql = "UPDATE movies SET title = ?, genre = ?, release_year = ?, description = ?, available = ? WHERE id = ?";
        jdbcTemplate.update(sql, movie.getTitle(), movie.getGenre(), movie.getReleaseYear(),
                movie.getDescription(), movie.isAvailable(), movie.getId());
    }

    public void deleteMovie(Integer id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void addMovie(Movie movie) {
        String sql = "INSERT INTO movies (title, genre, release_year, description, available) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, movie.getTitle(), movie.getGenre(), movie.getReleaseYear(),
                movie.getDescription(), movie.isAvailable());
    }

    public Movie getMovieById(Integer id) {
        String sql = "SELECT * FROM movies WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, movieRowMapper);
    }

    public List<Movie> getAllMovies() {
        String sql = "SELECT * FROM movies";
        return jdbcTemplate.query(sql, movieRowMapper);
    }

    public List<Movie> getAvailableMovies() {
        String sql = "SELECT * FROM movies WHERE available = TRUE";
        return jdbcTemplate.query(sql, movieRowMapper);
    }

    public List<Movie> searchMovies(String query) {
        String sql = "SELECT * FROM movies WHERE LOWER(title) LIKE ? OR LOWER(genre) LIKE ? OR LOWER(description) LIKE ?";
        String searchTerm = "%" + query.toLowerCase() + "%";
        return jdbcTemplate.query(sql, new Object[]{searchTerm, searchTerm, searchTerm}, movieRowMapper);
    }


}
