package pl.edu.wisniewski.movie;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wisniewski.movie.domain.Director;
import pl.edu.wisniewski.movie.domain.Movie;
import pl.edu.wisniewski.movie.service.MovieManager;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/beans.xml"})
@Commit
@Transactional(transactionManager = "txManager")
public class MovieManagerTest {
    @Autowired
    MovieManager movieManager;

    private List<Long> movieIds;
    private List<Long> directorsIds;


    @Before
    public void setup() {
        movieIds = new LinkedList<>();
        movieIds.add(movieManager.addMovie(new Movie("Aaaa", 120)));
        movieIds.add(movieManager.addMovie(new Movie("Bbbb", 100)));
        Movie movie = movieManager.findMovieById(movieIds.get(0));
        Movie movie1 = movieManager.findMovieById(movieIds.get(1));
        directorsIds = new LinkedList<>();
        directorsIds.add(movieManager.addDirector(new Director("Adam", new LinkedList<Movie>(Arrays.asList(movie)))));
        directorsIds.add(movieManager.addDirector(new Director("Jan", new LinkedList<Movie>(Arrays.asList(movie1)))));
    }

    @Test
    public void addMovieTest() {
        assertTrue(movieIds.size() > 0);
    }

    @Test
    public void getAllMoviesTest() {
        List<Long> foundIds = new LinkedList<>();
        for (Movie movie : movieManager.findAllMovies()) {
            if (movieIds.contains(movie.getId())) foundIds.add(movie.getId());
        }
        assertEquals(movieIds.size(), foundIds.size());
    }

    @Test
    public void getMovieByIdTest() {
        Movie movie = movieManager.findMovieById(movieIds.get(0));
        assertEquals("Aaaa", movie.getTitle());
    }

    @Test
    public void deleteMovieTest() {

        Movie movie = movieManager.findMovieById(movieIds.get(0));
        movieManager.deleteMovie(movie);
        assertNull(movieManager.findMovieById(movie.getId()));
    }

    @Test
    public void updateMovieTest() {
        Movie movie = movieManager.findMovieById(movieIds.get(1));
        movie.setTitle("Cccc");
        movieManager.updateMovie(movie);
        assertEquals("Cccc", movieManager.findMovieById(movie.getId()).getTitle());
    }

    @Test
    public void findMoviesByTitleTest() {
        List<Movie> movies = movieManager.findMoviesByTitle("Aaa");
        assertEquals(2, movies.size());
    }

    @Test
    public void findMoviesByDirector() {
        Director director = movieManager.findDirectorById(directorsIds.get(1));
        List<Movie> movies = movieManager.getAllMoviesForDirector(director);
        assertEquals(1, movies.size());
    }

    @Test
    public void transferMovieToAnotherDirector() {
        Movie movie = movieManager.findMovieById(movieIds.get(0));
        Movie movie1 = movieManager.findMovieById(movieIds.get(1));
        Director director = movieManager.findDirectorById(directorsIds.get(0));
        Director director1 = movieManager.findDirectorById(directorsIds.get(1));
        movieManager.transferMovieToAnotherDirector(
                movie, movie1, director, director1);
        assertEquals("Bbbb",movieManager.getAllMoviesForDirector(director).get(0).getTitle());
        assertEquals(1,movieManager.getAllMoviesForDirector(director1).size());

    }
}