package pl.edu.wisniewski.movie.domain;


import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Movie")
@Table(name = "Movie")
@NamedQueries({

        @NamedQuery(name = "movie.all", query = "Select p from Movie p"),
        @NamedQuery(name = "movie.findMoviesByTitle", query = "Select c from Movie c where c.title like :titleNameFragment")
})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Integer duration;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(duration, movie.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, duration);
    }
}