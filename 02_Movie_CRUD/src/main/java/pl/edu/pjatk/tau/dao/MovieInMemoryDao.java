package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieInMemoryDao implements Dao<Movie> {
    protected List<Movie> movies = new ArrayList<>();

    @Override
    public Long save(Movie o) {
        movies.add(o);
        return o.getId();
    }
}
