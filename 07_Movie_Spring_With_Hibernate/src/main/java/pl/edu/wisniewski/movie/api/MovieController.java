package pl.edu.wisniewski.movie.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.edu.wisniewski.movie.domain.Movie;
import pl.edu.wisniewski.movie.service.MovieManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MovieController {
    @Autowired
    MovieManager movieManager;

    @RequestMapping("/")
    public String index() {
        return "Hello";
    }

    @RequestMapping("/movies")
    public List<Movie> getMovies() {
        List<Movie> movies = new LinkedList<>();
        for (Movie m : movieManager.findAllMovies()) {
            movies.add(m.clone());
        }
        return movies;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    public Movie addMovie(@RequestBody Movie nmovie) {
        nmovie.setId(movieManager.addMovie(nmovie));
        return nmovie;
    }

    @RequestMapping(value = "/movie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Movie getMovie(@PathVariable("id") Long id) throws SQLException {
        return movieManager.findMovieById(id).clone();
    }

    @RequestMapping(value = "/movie", method = RequestMethod.PUT)
    public String updateMOvie(@RequestBody Movie nmovie) {

        movieManager.updateMovie(nmovie);
        return "OK";
    }

    @RequestMapping(value = "/movie/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteMovie(@PathVariable("id") Long id) throws SQLException {
        movieManager.deleteMovie(movieManager.findMovieById(id));
        return "OK";
    }
}
