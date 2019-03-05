package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieInMemoryDao implements Dao<Movie> {
    protected List<Movie> movies = new ArrayList<>();

    @Override
    public Long save(Movie o) throws IllegalArgumentException{
        if(movies.stream().anyMatch(x->x.getId().equals(o.getId())))
            throw new IllegalArgumentException("Movie exist in db");
        movies.add(o);
        return o.getId();
    }

    @Override
    public List<Movie> getAll() {
        return movies;
    }

    @Override
    public Optional<Movie> getById(Long id) throws IllegalArgumentException{
        if(!movies.stream().anyMatch(x->x.getId().equals(id)))
            throw new IllegalArgumentException("Movie not exist");
        return movies.stream().filter(x->x.getId().equals(id)).findFirst();
    }

}
