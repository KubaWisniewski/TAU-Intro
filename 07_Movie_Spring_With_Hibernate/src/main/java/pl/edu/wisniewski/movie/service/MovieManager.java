package pl.edu.wisniewski.movie.service;

import pl.edu.wisniewski.movie.domain.Movie;

import java.util.List;

public interface MovieManager {
    Long addMovie(Movie movie);
    void updateMovie(Movie movie);
    Movie findMovieById(Long id);
    void deleteMovie(Movie movie);
    List<Movie> findAllMovies();
    List<Movie> findMoviesByTitle(String titleNameFragment);
}