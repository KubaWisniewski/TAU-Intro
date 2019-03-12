package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Movie;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MovieDao {
    Connection getConnection();

    void setConnection(Connection connection) throws SQLException;

    int addMovie(Movie movie);

    List<Movie> getAllMovies();

    Movie getMovie(long id) throws SQLException;
     int updateMovie(Movie movie) throws SQLException;


}
