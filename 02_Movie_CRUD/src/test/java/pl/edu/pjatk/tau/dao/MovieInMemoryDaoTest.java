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
    @Test(expected = IllegalArgumentException.class)
    public void checkSavingExist(){
            Movie movie =new Movie(1L,"C",100);
            movieDao.save(movie);
    }

    @Test
    public void checkGettingAllMovies(){
        Assert.assertArrayEquals(movieDao.movies.toArray(),movieDao.getAll().toArray());
        Assert.assertEquals(movieDao.movies.size(),movieDao.getAll().size());
    }

    @Test
   public void checkGettingById(){
        Movie movie= new Movie(3L, "C",105);
        movieDao.save(movie);
        Assert.assertEquals(movie,movieDao.getById(3L).get());
    }
    @Test(expected = IllegalArgumentException.class)
    public void checkGettingExist(){
        movieDao.getById(3L);


    }

}
