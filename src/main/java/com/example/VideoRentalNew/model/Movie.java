package com.example.VideoRentalNew.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INTEGER")
    private Integer id;
    private String title;
    private String genre;
    private int release_year;
    private String description;
    @Column(name = "available")
    private boolean available = true;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private Set<Review> reviews;

    // Default constructor
    public Movie() {
    }

    // Parameterized constructor
    public Movie(String title, String genre, int release_year, String description) {
        this.title = title;
        this.genre = genre;
        this.release_year = release_year;
        this.description = description;
    }

    // Getter and Setter for id
    public int getId() {
        return Math.toIntExact(id);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Getter and Setter for release_year
    public int getReleaseYear() {
        return release_year;
    }

    public void setReleaseYear(int release_year) {
        this.release_year = release_year;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Override toString() for a string representation of the movie
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", release_year=" + release_year +
                ", description=" + description +
                ", available=" + available +
                '}';
    }

    // Override equals() to compare movie objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return id != null ? id.equals(movie.id) : movie.id == null;
    }

    // Override hashCode() to generate a hash code based on movie id
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
