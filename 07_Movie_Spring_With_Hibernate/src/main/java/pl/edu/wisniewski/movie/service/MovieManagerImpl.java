package pl.edu.wisniewski.movie.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wisniewski.movie.domain.Director;
import pl.edu.wisniewski.movie.domain.Movie;

import java.util.List;

@Component
@Transactional
public class MovieManagerImpl implements MovieManager {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Movie findMovieById(Long id) {
        return (Movie) sessionFactory.getCurrentSession().get(Movie.class, id);
    }

    @Override
    public List<Movie> findAllMovies() {
        return sessionFactory.getCurrentSession().getNamedQuery("movie.all").list();
    }

    @Override
    public List<Movie> findMoviesByTitle(String titleNameFragment) {
        return (List<Movie>) sessionFactory.getCurrentSession()
                .getNamedQuery("movie.findMoviesByTitle")
                .setString("titleNameFragment", "%" + titleNameFragment + "%")
                .list();
    }

    @Override
    public List<Movie> getAllMoviesForDirector(Director director) {
        return (List<Movie>) sessionFactory.getCurrentSession()
                .getNamedQuery("movie.findMoviesByDir   ector")
                .setParameter("director", director)
                .list();
    }

    @Override
    public Long addMovie(Movie movie) {
        return (Long) sessionFactory.getCurrentSession().save(movie);
    }

    @Override
    public void updateMovie(Movie movie) {
        sessionFactory.getCurrentSession().update(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        sessionFactory.getCurrentSession().delete(movie);
    }

    @Override
    public Long addDirector(Director director) {
        if (director.getId() != null)
            throw new IllegalArgumentException("the director ID should be null if added to database");
        sessionFactory.getCurrentSession().persist(director);
        for (Movie movie : director.getMovies()) {
            movie.setDirector(director);
            sessionFactory.getCurrentSession().update(movie);
        }
        sessionFactory.getCurrentSession().flush();
        return director.getId();
    }

    /*
        @Override
        public List<Director> findAllDirectors() {
            return sessionFactory.getCurrentSession().getNamedQuery("director.all").list();
        }
    */
    @Override
    public Director findDirectorById(Long id) {
        return (Director) sessionFactory.getCurrentSession().get(Director.class, id);
    }
/*
    @Override
    public void updateDirector(Director director) {
        sessionFactory.getCurrentSession().update(director);
    }

    @Override
    public void deleteDirector(Director director) {
        sessionFactory.getCurrentSession().delete(director);
    }
    */

    @Override
    public void transferMovieToAnotherDirector(Movie movie1, Movie movie2, Director director1, Director director2) {

        movie1.setDirector(director2);
        sessionFactory.getCurrentSession().save(director2);
        sessionFactory.getCurrentSession().save(movie1);

        movie2.setDirector(director1);
        sessionFactory.getCurrentSession().save(director1);
        sessionFactory.getCurrentSession().save(movie2);
    }
}