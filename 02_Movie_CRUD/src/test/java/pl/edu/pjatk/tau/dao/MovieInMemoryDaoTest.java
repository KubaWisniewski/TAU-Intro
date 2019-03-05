package pl.edu.pjatk.tau.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pjatk.tau.domain.Movie;

import java.util.Collections;

public class MovieInMemoryDaoTest {
    MovieInMemoryDao movieDao;

    @Before
    public void setup() {
        movieDao = new MovieInMemoryDao();
        Collections.addAll(movieDao.movies,
                new Movie(1L, "A", 110),
                new Movie(2L, "B", 130));
    }

    @Test
    public void checkDaoObject() {
        Assert.assertNotNull(movieDao);
    }

    @Test
    public void checkSaving() {
        Movie movie = new Movie(3L, "Kingdom", 100);
        Assert.assertEquals(3L,movieDao.save(movie).longValue());
        Assert.assertEquals(movieDao.movies.size(),3);
    }
}
