package pl.edu.wisniewski.movie.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "Director")
@NamedQuery(name = "director.all", query = "Select d from Director d")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "director"
    )
    private List<Movie> movies;

    public Director() {
    }

    public Director(String firstName, List<Movie> movies) {
        this.firstName = firstName;
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMoviess(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return Objects.equals(id, director.id) &&
                Objects.equals(firstName, director.firstName) &&
                Objects.equals(movies, director.movies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, movies);
    }
}
