package pl.edu.pjatk.tau.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MovieTest {

    @Test
    public void checkMovie() {
        Movie movie = new Movie();
        Assert.assertNotNull(movie);
    }

    @Test
    public void checkGetterAndSetteer() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Kingdom");
        movie.setDuration(120);
        Assert.assertEquals(1L, movie.getId().longValue());
        Assert.assertEquals("Kingdom", movie.getTitle());
        Assert.assertEquals(120, movie.getDuration());
    }
}
