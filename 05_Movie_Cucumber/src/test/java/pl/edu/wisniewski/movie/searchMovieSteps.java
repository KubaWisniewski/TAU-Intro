package pl.edu.wisniewski.movie;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.edu.wisniewski.movie.dao.MovieInMemoryDao;
import pl.edu.wisniewski.movie.domain.Movie;
import pl.edu.wisniewski.movie.service.MovieService;

import java.util.List;

public class searchMovieSteps {
    private MovieInMemoryDao movieInMemoryDao = new MovieInMemoryDao();
    private MovieService movieService = new MovieService();
    private Movie searchMovie;


    @Given("cinema")
    public void dataSource() {
        movieService.setData(movieInMemoryDao);
    }

    @Given("^I have 3 movies in$")
    public void dataWithThreeMovies() {
        movieInMemoryDao.save(new Movie(1L, "A1", 120));
        movieInMemoryDao.save(new Movie(2L, "A4", 100));
        movieInMemoryDao.save(new Movie(3L, "A5", 110));
    }

    @When("^Spectator ask about \"([^\"]*)\"$")
    public void searchRecords(String regex) {
        searchMovie = movieService.findMoviesByRegex(regex);
    }

    @Then("^Then should get \"([^\"]*)\"$")
    public void oneRecordShouldBeFound(String answer) {
        String systemAnswer;
        if (searchMovie.getTitle().equals("A1"))
            systemAnswer = "Yes";
        else
            systemAnswer = "No";
        Assert.assertEquals(answer, systemAnswer);

    }

}
