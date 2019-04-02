package pl.edu.wisniewski.movie;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.edu.wisniewski.movie.dao.MovieInMemoryDao;
import pl.edu.wisniewski.movie.domain.Movie;

public class AddMovieSteps {
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
