package com.example.VideoRentalNew.repository;

import com.example.VideoRentalNew.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByGenre(String genre);
    List<Movie> findByTitleContainingIgnoreCase(String keywords);
    @Query(value = "SELECT * FROM movies WHERE available = true ORDER BY id DESC LIMIT :limit", nativeQuery = true)
    List<Movie> findByAvailableTrueOrderByIdDesc(int limit);
}
