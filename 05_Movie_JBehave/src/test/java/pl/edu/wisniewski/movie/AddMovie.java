package pl.edu.wisniewski.movie;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.edu.wisniewski.movie.dao.MovieInMemoryDao;
import pl.edu.wisniewski.movie.domain.Movie;

public class AddMovie {
    private MovieInMemoryDao movieInMemoryDao = new MovieInMemoryDao();
    private Movie movie = new Movie();

    @Given("^I have movie with title \"([^\"]*)\"$$")
    public void setMovieTitle(String title) {
        movie.setTitle(title);
    }

    @When("^I want set duration on \"([^\"]*)\"$$")
    public void setMovieDuration(String duration) {
        movie.setDuration(Integer.parseInt(duration));
        movieInMemoryDao.save(movie);
    }

    @Then("^Should be 1 movie in cinema list.$")
    public void checkMovieList() {
        Assert.assertEquals(movieInMemoryDao.getAll().size(), 1);
    }
}
