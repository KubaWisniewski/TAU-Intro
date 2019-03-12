package pl.edu.pjatk.tau.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pjatk.tau.domain.Movie;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;


public class MovieDaoTest {

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    MovieDao movieManager;
    List<Movie> expectedDbState;

    @Before
    public void setup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.createStatement()
                    .executeUpdate("CREATE TABLE " +
                            "Movie(id bigint GENERATED BY DEFAULT AS IDENTITY, " +
                            "title varchar(20) NOT NULL, " +
                            "duration integer)");

        } catch (SQLException e) {
        }

        Random rand = new Random();
        PreparedStatement addMovieStmt = connection.prepareStatement(
                "INSERT INTO Movie (title, duration) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        expectedDbState = new LinkedList<Movie>();
        for (int i = 0; i < 10; i++) {
            Movie movie = new Movie("A" + rand.nextInt(1000), 1000 + rand.nextInt(1000));
            try {
                addMovieStmt.setString(1, movie.getTitle());
                addMovieStmt.setInt(2, movie.getDuration());
                addMovieStmt.executeUpdate();
                ResultSet generatedKeys = addMovieStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    movie.setId(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
            }

            expectedDbState.add(movie);
        }
        movieManager = new MovieDaoJdbcImpl(connection);
    }

    @After
    public void cleanup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.prepareStatement("DELETE FROM Movie").executeUpdate();
        } catch (Exception e) {
            System.out.println("Probably the database was not yet initialized");
        }
    }

    @Test
    public void checkAdding() {
        Movie movie = new Movie();
        movie.setTitle("B");
        movie.setDuration(100);
        Assert.assertEquals(1, movieManager.addMovie(movie));
        expectedDbState.add(movie);
        Assert.assertThat(movieManager.getAllMovies(), equalTo(expectedDbState));
    }

    @Test
    public void checkGettingAll() {
        Assert.assertThat(movieManager.getAllMovies(), equalTo(expectedDbState));
    }

    @Test
    public void checkGettingById() throws Exception {
        Movie movie = expectedDbState.get(5);
        Assert.assertEquals(movie, movieManager.getMovie(movie.getId()));
    }

    @Test(expected = Exception.class)
    public void checkGettingByIdException() throws Exception {
        Movie movie = expectedDbState.get(12);
        Assert.assertEquals(movie, movieManager.getMovie(movie.getId()));

    }

    @Test()
    public void checkUpdatingSuccess() throws SQLException {
        Movie m = expectedDbState.get(3);
        m.setTitle("B");
        expectedDbState.set(3, m);
        Assert.assertEquals(1, movieManager.updateMovie(m));
        Assert.assertThat(movieManager.getAllMovies(), equalTo(expectedDbState));
    }

    @Test(expected = SQLException.class)
    public void checkUpdatingFailure() throws SQLException {
        Movie m = new Movie("B", 123);
        Assert.assertEquals(1, movieManager.updateMovie(m));
    }

    @Test(expected = SQLException.class)
    public void checkDeleting() throws SQLException {
        Movie m = expectedDbState.get(3);
        expectedDbState.remove(m);
        Assert.assertEquals(1, movieManager.deleteMovie(m));
        Assert.assertThat(movieManager.getAllMovies(), equalTo(expectedDbState));
        Assert.assertNull(movieManager.getMovie(m.getId()));
    }

}