package pl.edu.wisniewski.movie.domain;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Movie")
@Table(name = "Movie")
@NamedQueries({

        @NamedQuery(name = "movie.all", query = "Select p from Movie p"),
        @NamedQuery(name = "movie.findMoviesByTitle", query = "Select c from Movie c where c.title like :titleNameFragment"),
        @NamedQuery(name = "movie.findMoviesByDirector", query = "Select c from Movie c where c.director= :director")

})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Integer duration;
    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Movie() {
    }

    public Movie(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public Movie(Long id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Movie clone() {
        Movie p = new Movie();
        p.director = null;
        p.id = id;
        p.title = title;
        p.duration = duration;
        return p;
    }

    @Override
    public boolean equals(Object o) {
        return o.toString().equals(this.toString());

    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "<ERROR>";
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, duration);
    }
}