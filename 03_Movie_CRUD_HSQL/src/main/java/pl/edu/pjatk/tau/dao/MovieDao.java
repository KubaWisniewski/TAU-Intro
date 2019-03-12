package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Movie;

import java.sql.Connection;
import java.sql.SQLException;

public interface MovieDao {
    Connection getConnection();

    void setConnection(Connection connection) throws SQLException;

    int addMovie(Movie movie);
}
