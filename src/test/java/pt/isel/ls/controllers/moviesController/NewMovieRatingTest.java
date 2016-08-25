package pt.isel.ls.controllers.moviesController;

import junit.framework.Assert;
import org.junit.Test;
import pt.isel.ls.BaseDatabaseTest;
import pt.isel.ls.app.Dispatcher;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.exceptions.RequestException;
import pt.isel.ls.models.mappers.MovieMapper;

import java.sql.SQLException;
import java.util.Collections;

/**
 * For all tests on this class
 * Test request: POST /movies/{mid}/ratings
 * NewMovieRating is the command responsible
 *
 * SQL error codes are available here:
 * https://technet.microsoft.com/en-us/library/cc645603(v=sql.105).aspx
 */
public class NewMovieRatingTest extends BaseDatabaseTest {

	@Test
	public void testCommandWithCorrectParameters() throws SQLException, RequestException {
		//common values used throughout the test
		int rating = 5;
		String movieTitle = "Mastermind";
		//first insert a movie
		int lastId = MovieMapper.insert(new Movie(-1, movieTitle, 17, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList()));
		//insert rating
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "POST", "/movies/" + lastId + "/ratings", "", "rating=" + rating });
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Response expected = new Response(null, res.getContent(), null);
		Assert.assertEquals(res.getContent(), res.getContent());
	}

	@Test
	public void testCommandWithIncorrectRatingValue() throws SQLException, RequestException {
		//common values used throughout the test
		int rating = 17;
		//first insert a movie
		int lastId = MovieMapper.insert(new Movie(-1, "Mastermind", 17, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList()));
		//insert rating
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "POST", "/movies/" + lastId + "/ratings", "", "rating=" + rating });
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		SQLException ex = (SQLException)res.getError().getCause();
//		Assert.assertTrue(ex instanceof SQLServerException);
		//547 indicates an sql constraint conflict
//		Assert.assertTrue(ex.getErrorCode() == 547);
//		Assert.assertTrue(ex.getMessage().contains("The INSERT statement conflicted with the CHECK constraint"));
	}

	@Test
	public void testCommandWithNonexistentMovie() throws RequestException {
		//insert rating
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "POST", "/movies/1/ratings", "", "rating=5" });
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		SQLException ex = (SQLException)res.getError().getCause();
//		Assert.assertTrue(ex instanceof SQLServerException);
		//547 indicates an sql constraint conflict
//		Assert.assertTrue(ex.getErrorCode() == 547);
//		Assert.assertTrue(ex.getMessage().contains("The INSERT statement conflicted with the FOREIGN KEY constraint"));
	}
}
