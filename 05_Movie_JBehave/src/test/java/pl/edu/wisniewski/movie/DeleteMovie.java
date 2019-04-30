package pl.edu.wisniewski.movie;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.edu.wisniewski.movie.dao.MovieInMemoryDao;
import pl.edu.wisniewski.movie.domain.Movie;
import pl.edu.wisniewski.movie.service.MovieService;

import java.util.ArrayList;
import java.util.List;

public class DeleteMovie {
    private MovieInMemoryDao movieInMemoryDao = new MovieInMemoryDao();
    private MovieService movieService = new MovieService();
    private List<Movie> deleteMovies = new ArrayList<>();


    @Given("^I have 3 movies in cinema$")
    public void dataWithThreeMovies() {
        movieInMemoryDao.save(new Movie(1L, "A1", 120));
        movieInMemoryDao.save(new Movie(2L, "A2", 100));
        movieInMemoryDao.save(new Movie(3L, "A3", 110));
        movieService.setData(movieInMemoryDao);
    }

    @Given("^I have list of movies to delete$")
    public void listOfMoviesToDelete() {
        deleteMovies.add(movieInMemoryDao.getById(2L).get());
        deleteMovies.add(movieInMemoryDao.getById(1L).get());
    }

    @When("^I want to delete one of them. \"([^\"]*)\"$")
    public void deleteMovies(String title) {
        movieService.deleteMovie(title);
    }

    @Then("^Should be 2 movies in cinema list.$")
    public void oneRecordShouldBeFound() {
        Assert.assertTrue(movieInMemoryDao.getAll().size() == 2);

    }
}
