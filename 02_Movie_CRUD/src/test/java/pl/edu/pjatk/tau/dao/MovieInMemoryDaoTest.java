package pl.edu.pjatk.tau.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MovieInMemoryDaoTest {
    MovieInMemoryDao movieDao;

    @Before
    public void setup(){
        movieDao=new MovieInMemoryDao();
    }

    @Test
    public void checkDaoObject(){
        Assert.assertNotNull(movieDao);
    }
}
