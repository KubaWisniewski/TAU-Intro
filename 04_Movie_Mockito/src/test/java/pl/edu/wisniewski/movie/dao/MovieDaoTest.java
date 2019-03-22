package pl.edu.wisniewski.movie.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.edu.wisniewski.movie.domain.Movie;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieDaoTest {

    Random random;
    static List<Movie> initialDatabaseState;


    abstract class AbstractResultSet implements ResultSet {
        int i;

        @Override
        public int getInt(String s) throws SQLException {
            return initialDatabaseState.get(i - 1).getDuration();
        }

        @Override
        public long getLong(String s) throws SQLException {
            return initialDatabaseState.get(i - 1).getId();
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return initialDatabaseState.get(i - 1).getTitle();
        }

        @Override
        public boolean next() throws SQLException {
            i++;
            if (i > initialDatabaseState.size())
                return false;
            else
                return true;
        }
    }

    @Mock
    Connection connection;
    @Mock
    PreparedStatement selectStatementMock;
    @Mock
    PreparedStatement insertStatementMock;
    @Mock
    PreparedStatement selectByIdStatementMock;

    @Before
    public void setup() throws SQLException {
        random = new Random();
        initialDatabaseState = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Movie movie = new Movie();
            movie.setId(new Long(i));
            movie.setTitle("A" + random.nextInt(1000));
            movie.setDuration(random.nextInt(50) + 1950);
            initialDatabaseState.add(movie);
        }
        when(connection.prepareStatement("SELECT id, title, duration FROM Movie ORDER BY id")).thenReturn(selectStatementMock);
        when(connection.prepareStatement("INSERT INTO Movie (title, duration) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)).thenReturn(insertStatementMock);
        when(connection.prepareStatement("SELECT id, title, duration FROM Movie WHERE id = ?")).thenReturn(selectByIdStatementMock);
    }

    @Test
    public void setConnectionCheck() throws SQLException {
        MovieDaoJdbcImpl dao = new MovieDaoJdbcImpl();
        dao.setConnection(connection);
        Assert.assertNotNull(dao.getConnection());
        Assert.assertEquals(dao.getConnection(), connection);
    }

    @Test
    public void setConnectionCreatesQueriesCheck() throws SQLException {
        MovieDaoJdbcImpl dao = new MovieDaoJdbcImpl();
        dao.setConnection(connection);
        Assert.assertNotNull(dao.preparedStatementGetAll);
        Mockito.verify(connection).prepareStatement("SELECT id, title, duration FROM Movie ORDER BY id");
    }

    @Test
    public void getAllCheck() throws SQLException {

        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("title")).thenCallRealMethod();
        when(mockedResultSet.getInt("duration")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);


        MovieDaoJdbcImpl dao = new MovieDaoJdbcImpl();
        dao.setConnection(connection);
        List<Movie> retrievedMovies = dao.getAllMovies();
        Assert.assertThat(retrievedMovies, equalTo(initialDatabaseState));


        Mockito.verify(selectStatementMock, times(1)).executeQuery();
        Mockito.verify(mockedResultSet, times(initialDatabaseState.size())).getLong("id");
        Mockito.verify(mockedResultSet, times(initialDatabaseState.size())).getString("title");
        Mockito.verify(mockedResultSet, times(initialDatabaseState.size())).getInt("duration");
        Mockito.verify(mockedResultSet, times(initialDatabaseState.size() + 1)).next();
    }

    @Test
    public void checkAddingInOrder() throws Exception {


        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);


        MovieDaoJdbcImpl dao = new MovieDaoJdbcImpl();
        dao.setConnection(connection);
        Movie movie = new Movie();
        movie.setTitle("B");
        movie.setDuration(120);
        dao.addMovie(movie);


        inorder.verify(insertStatementMock, times(1)).setString(1, "B");
        inorder.verify(insertStatementMock, times(1)).setInt(2, 120);
        inorder.verify(insertStatementMock).executeUpdate();
    }

    @Test
    public void checkGettingById() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenReturn(initialDatabaseState.get(5).getId());
        when(mockedResultSet.getString("title")).thenReturn(initialDatabaseState.get(5).getTitle());
        when(mockedResultSet.getInt("duration")).thenReturn(initialDatabaseState.get(5).getDuration());
        when(selectByIdStatementMock.executeQuery()).thenReturn(mockedResultSet);


        MovieDaoJdbcImpl dao = new MovieDaoJdbcImpl();
        dao.setConnection(connection);
        Movie retrievedMovie = dao.getMovie(5L);
        Assert.assertEquals(retrievedMovie, initialDatabaseState.get(5));

        Mockito.verify(selectByIdStatementMock, times(1)).setLong(1, 5);
        Mockito.verify(selectByIdStatementMock, times(1)).executeQuery();
        Mockito.verify(mockedResultSet, times(1)).getLong("id");
        Mockito.verify(mockedResultSet, times(1)).getString("title");
        Mockito.verify(mockedResultSet, times(1)).getInt("duration");
        Mockito.verify(mockedResultSet, times(1)).next();
    }

}
