package pl.edu.wisniewski.movie;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import pl.edu.wisniewski.movie.dao.MovieDaoTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(MovieDaoTest.class)
public class DatabaseTestSuite {
}
