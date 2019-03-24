package pl.edu.wisniewski.movie.dao;

import pl.edu.wisniewski.movie.domain.Movie;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MovieDaoJdbcImpl implements MovieDao {

    public PreparedStatement preparedStatementGetAll;
    public PreparedStatement preparedStatementInsert;
    public PreparedStatement preparedStatementGetMovie;
    public PreparedStatement preparedStatementDelete;
    public PreparedStatement preparedStatementUpdate;
    Connection connection;


    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        preparedStatementGetAll = connection.prepareStatement(
                "SELECT id, title, duration FROM Movie ORDER BY id");
        preparedStatementInsert = connection.prepareStatement(
                "INSERT INTO Movie (title, duration) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        preparedStatementGetMovie = connection.prepareStatement("SELECT id, title, duration FROM Movie WHERE id = ?");
        preparedStatementDelete = connection.prepareStatement("DELETE FROM Movie where id = ?");
        preparedStatementUpdate = connection.prepareStatement("UPDATE Movie SET title=?,duration=? WHERE id = ?");

    }

    @Override
    public List<Movie> getAllMovies() {
        try {
            List<Movie> ret = new LinkedList<>();
            ResultSet result = preparedStatementGetAll.executeQuery();
            while (result.next()) {
                Movie movie = new Movie();
                movie.setId(result.getLong("id"));
                movie.setTitle(result.getString("title"));
                movie.setDuration(result.getInt("duration"));
                ret.add(movie);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Movie getMovie(long id) throws SQLException {
        try {
            preparedStatementGetMovie.setLong(1, id);
            ResultSet rs = preparedStatementGetMovie.executeQuery();

            if (rs.next()) {
                Movie p = new Movie();
                p.setId(rs.getLong("id"));
                p.setTitle(rs.getString("title"));
                p.setDuration(rs.getInt("duration"));
                return p;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        throw new SQLException("Movie with id " + id + " does not exist");
    }

    @Override
    public int addMovie(Movie movie) throws SQLException {
        preparedStatementInsert.setString(1, movie.getTitle());
        preparedStatementInsert.setInt(2, movie.getDuration());
        int r = preparedStatementInsert.executeUpdate();
        return r;
    }


    @Override
    public int updateMovie(Movie movie) throws SQLException {
        int count = 0;
        try {
            preparedStatementUpdate.setString(1, movie.getTitle());
            preparedStatementUpdate.setInt(2, movie.getDuration());
            if (movie.getId() != null) {
                preparedStatementUpdate.setLong(3, movie.getId());
            } else {
                preparedStatementUpdate.setLong(3, -1);
            }
            count = preparedStatementUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        if (count <= 0)
            throw new SQLException("Movie not found for update");
        return count;
    }

    @Override
    public int deleteMovie(Movie movie) {
        try {
            preparedStatementDelete.setLong(1, movie.getId());
            int r = preparedStatementDelete.executeUpdate();
            return r;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }
}
