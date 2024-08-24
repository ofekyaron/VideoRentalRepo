package com.example.VideoRentalNew.repository;

import com.example.VideoRentalNew.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    // Existing methods
    List<Movie> findByGenre(String genre);
    List<Movie> findByTitleContainingIgnoreCase(String keywords);

    @Query(value = "SELECT * FROM movies WHERE available = true ORDER BY id DESC LIMIT :limit", nativeQuery = true)
    List<Movie> findByAvailableTrueOrderByIdDesc(int limit);

    // New methods to replace DAO functionality
    List<Movie> findByAvailableTrue();

    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(m.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Movie> searchMovies(@Param("query") String query);

    // Note: update, delete, and add operations are already handled by JpaRepository
}