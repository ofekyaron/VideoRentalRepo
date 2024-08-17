package com.example.VideoRentalNew.bean;

import com.example.VideoRentalNew.model.Movie;
import com.example.VideoRentalNew.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Component
@SessionScope
public class MovieBean implements Serializable {

    @Autowired
    private MovieService movieService;

    private String searchTerm;

    public String searchMovies() {
        // Perform search and return the name of the view to navigate to
        return "searchResults";
    }

    // Getters and setters
    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

}
