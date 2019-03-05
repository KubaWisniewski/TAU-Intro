package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Movie;

import java.util.List;

public interface Dao<T> {
    Long save(T o);
    List<T> getAll();
}
