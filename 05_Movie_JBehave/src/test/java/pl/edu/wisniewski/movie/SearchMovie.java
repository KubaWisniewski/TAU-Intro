package pl.edu.wisniewski.movie;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.edu.wisniewski.movie.dao.MovieInMemoryDao;
import pl.edu.wisniewski.movie.domain.Movie;
import pl.edu.wisniewski.movie.service.MovieService;

public class SearchMovie {
    private MovieInMemoryDao movieInMemoryDao = new MovieInMemoryDao();
    private MovieService movieService = new MovieService();
    private Movie searchMovie;

    private String checkMovieExist() {
        if (searchMovie.getTitle().equals("A1"))
            return "Yes";
        return "No";
    }

    @Given("cinema")
    public void dataSource() {
        movieService.setData(movieInMemoryDao);
    }

    @Given("^I have 3 movies in$")
    public void dataWithThreeMovies() {
        movieInMemoryDao.save(new Movie(1L, "A1", 120));
        movieInMemoryDao.save(new Movie(2L, "A2", 100));
        movieInMemoryDao.save(new Movie(3L, "A3", 110));
    }

    @When("^Spectator ask about \"([^\"]*)\"$")
    public void searchRecords(String regex) {
        searchMovie = movieService.findMoviesByRegex(regex);
    }

    @Then("^Then should get \"([^\"]*)\"$")
    public void oneRecordShouldBeFound(String answer) {
        String systemAnswer = checkMovieExist();
        Assert.assertEquals(answer, systemAnswer);

    }

}
