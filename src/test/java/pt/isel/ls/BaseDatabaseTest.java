package pt.isel.ls;

import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.app.App;
import pt.isel.ls.services.sql.ConnectionWrapper;
import pt.isel.ls.services.sql.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Base class for tests who need to interact with the movies
 */
public class BaseDatabaseTest {

	//an App instance is necessary to obtain a connection
	static App app;

	/**
	 * This method will be called only once per test class.
	 */
	@BeforeClass
	public static void setUp() throws IllegalAccessException, NoSuchFieldException, SQLException {
		app = new App(new String[]{});
	}

	@Before
	public void setUpDatabase() throws SQLException, NoSuchFieldException, IllegalAccessException {
		cleanDatabase();
	}

	@AfterClass
	public static void afterAllTestsRun() throws SQLException, NoSuchFieldException, IllegalAccessException {
		cleanDatabase();
	}

	/**
	 * Prepare the movies for the tests
	 * by cleaning each table
	 */
	private static void cleanDatabase() throws SQLException, NoSuchFieldException, IllegalAccessException {
		ConnectionWrapper connectionWrapper = new ConnectionWrapper();
		Connection connection = connectionWrapper.getConnection();
		// delete movie_collection
		PreparedStatement statement = connection.prepareStatement(Statements.DELETE_ALL_MOVIE_COLLECTIONS);
		statement.executeUpdate();
		// delete collection
		statement = connection.prepareStatement(Statements.DELETE_ALL_COLLECTIONS);
		statement.executeUpdate();
		// delete reviews
		statement = connection.prepareStatement(Statements.DELETE_ALL_REVIEWS);
		statement.executeUpdate();
		// delete ratings
		statement = connection.prepareStatement(Statements.DELETE_ALL_RATINGS);
		statement.executeUpdate();
		// delete movies
		statement = connection.prepareStatement(Statements.DELETE_ALL_MOVIES);
		statement.executeUpdate();
		JDBCUtils.closeJDBCObjects(new Object[]{connection, statement});
	}

	@Test
	public void test_connection() throws SQLException, NoSuchFieldException, IllegalAccessException {
		Connection con = App.getConnectionWrapper().getConnection();
		Assert.assertTrue(con.isValid(10));
		JDBCUtils.closeJDBCObjects(new Object[]{con});
	}
}
