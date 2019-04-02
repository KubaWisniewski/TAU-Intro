package pl.edu.wisniewski.movie.service;

import pl.edu.wisniewski.movie.dao.MovieInMemoryDao;
import pl.edu.wisniewski.movie.domain.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieService {
    private MovieInMemoryDao movieInMemoryDao;

    public void setData(MovieInMemoryDao movieInMemoryDao) {
        this.movieInMemoryDao = movieInMemoryDao;
    }

    public Movie findMoviesByRegex(String regex) {
        return movieInMemoryDao.getAll().stream().filter(x -> x.getTitle().matches(regex)).findFirst().get();
    }

    public void deleteMovie(String title) {
        movieInMemoryDao.delete(findMoviesByRegex(title));
    }

}
