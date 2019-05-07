package pl.edu.wisniewski.movie.service;

import pl.edu.wisniewski.movie.domain.Director;
import pl.edu.wisniewski.movie.domain.Movie;

import java.util.List;

public interface MovieManager {
    Long addMovie(Movie movie);

    void updateMovie(Movie movie);

    Movie findMovieById(Long id);

    void deleteMovie(Movie movie);

    List<Movie> findAllMovies();

    List<Movie> findMoviesByTitle(String titleNameFragment);

    Long addDirector(Director director);

    List<Movie> getAllMoviesForDirector(Director director);

    // List<Director> findAllDirectors();
    Director findDirectorById(Long id);

    // void deleteDirector(Director director);
    // void updateDirector(Director director);
    void transferMovieToAnotherDirector(Movie movie1, Movie movie2, Director director1, Director director2);


}