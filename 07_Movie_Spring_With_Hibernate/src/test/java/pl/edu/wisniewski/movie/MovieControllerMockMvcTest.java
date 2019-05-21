package pl.edu.wisniewski.movie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.wisniewski.movie.domain.Movie;
import pl.edu.wisniewski.movie.service.MovieManager;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieManager service;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(mockMvc);
    }

    @Test
    public void greetingShouldReturnHelloMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello")));
    }

    @Test
    public void getAllShouldReturnEmptyResults() throws Exception {
        when(service.findAllMovies()).thenReturn(new LinkedList<Movie>());
        this.mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getAllShouldReturnSomeResults() throws Exception {
        List<Movie> expectedResult = new LinkedList<Movie>();
        Movie np = new Movie();
        np.setId(12L);
        np.setTitle("Aaa");
        expectedResult.add(np);
        when(service.findAllMovies()).thenReturn(expectedResult);
        this.mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":12,\"title\":\"Aaa\"}]"));
    }

    @Test
    public void postNewMovieShouldReallyAddItToDatabase() throws Exception {
        Movie p = new Movie();
        p.setTitle("Bbb");
        p.setDuration(120);
        when(service.addMovie(p)).thenReturn(0L);
        this.mockMvc.perform(post("/movies")
                .content("{\"title\":\"Bbb\"," +
                        "\"duration\":\"120\"}")

                .contentType("application/json"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0,\"title\":\"Bbb\",\"duration\":120,\"director\":null}"));
        p.setId(0L);
        verify(service).addMovie(p);
    }

    @Test
    public void deleteShouldRemoveMovieFromDatabase() throws Exception {
        Movie p = new Movie();
        p.setId(1L);
        p.setTitle("Bbb");
        p.setDuration(120);
        when(service.findMovieById(1L)).thenReturn(p);
        this.mockMvc.perform(delete("/movie/" + p.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));

        verify(service).deleteMovie(p);

    }

    @Test
    public void getShouldReturnMovieFromDatabase() throws Exception {
        Movie p = new Movie();
        p.setId(1L);
        p.setTitle("Bbb");
        p.setDuration(120);
        when(service.findMovieById(1L)).thenReturn(p);
        this.mockMvc.perform(get("/movie/" + p.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"title\":\"Bbb\",\"duration\":120,\"director\":null}"));
        verify(service).findMovieById(1L);

    }

    @Test
    public void putShouldReturnUpdatedMovieFromDatabase() throws Exception {
        Movie p = new Movie();
        p.setId(1L);
        p.setTitle("Bbb");
        p.setDuration(120);
        this.mockMvc.perform(put("/movie")
                .content("{\"id\":1,\"title\":\"Aaa\",\"duration\":120,\"director\":null}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
        p.setTitle("Aaa");
        verify(service).updateMovie(p);

    }
}

