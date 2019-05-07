package pl.edu.wisniewski.movie.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
                .setString("titleNameFragment", "%"+titleNameFragment+"%")
                .list();    }

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
}