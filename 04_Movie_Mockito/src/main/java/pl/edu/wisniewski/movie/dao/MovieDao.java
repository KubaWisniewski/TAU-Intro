package pl.edu.wisniewski.movie.dao;

import pl.edu.wisniewski.movie.domain.Movie;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MovieDao {
    public Connection getConnection();

    public void setConnection(Connection connection) throws SQLException;

    public List<Movie> getAllMovies();

    public int addMovie(Movie movie) throws SQLException;

    public Movie getMovie(long id) throws SQLException;
}
