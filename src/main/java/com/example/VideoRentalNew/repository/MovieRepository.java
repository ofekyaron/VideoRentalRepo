package com.example.VideoRentalNew.repository;

import com.example.VideoRentalNew.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
