package pt.isel.ls.controllers.moviesController;

import junit.framework.Assert;
import org.junit.Test;
import pt.isel.ls.BaseDatabaseTest;
import pt.isel.ls.app.Dispatcher;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.content.ContentMovieRatings;
import pt.isel.ls.models.exceptions.RatingException;
import pt.isel.ls.models.exceptions.RequestException;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.models.mappers.RatingMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Collections;

/**
 * For all tests on this class
 * Test request: GET /movies/{mid}/ratings
 * ShowMovieRatings is the command responsible.
 *
 * SQL error codes are available here:
 * https://technet.microsoft.com/en-us/library/cc645603(v=sql.105).aspx
 */
public class ShowMovieRatingsTest extends BaseDatabaseTest {

	@Test
	public void testCommandsHandlerWithNonexistentMovie() throws RequestException {
		//get ratings
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "GET", "/movies/1/ratings", "", ""});
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		SQLException ex = (SQLException)res.getError().getCause();
//		Assert.assertTrue(ex instanceof SQLServerException);
//		Assert.assertTrue(ex.getErrorCode() == 0);
//		Assert.assertEquals(ex.getMessage(), "The result set has no current row.");
	}

	@Test
	public void testCommandWithNoRatings() throws SQLException, RequestException {
		//first insert a movie
		int lastId = MovieMapper.insert(new Movie(-1, "GhostInTheShell", 17, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList()));
		//get movie inserted
		Movie movie = MovieMapper.get(lastId);
		//get ratings
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "GET", "/movies/" + lastId + "/ratings", "", ""});
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
		Response expected = new Response(HttpStatusCode.Ok, new ContentMovieRatings(movie), null);
		Assert.assertEquals(expected.getContent(), res.getContent());
	}

	@Test
	public void testCommandWithMultipleRatings() throws SQLException, RequestException {
		//first insert a movie
		int lastId = MovieMapper.insert(new Movie(-1, "Ghost In The Shell", 17, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList()));
		//get movie inserted
		Movie movie = MovieMapper.get(lastId);
		//insert 5 ratings
		for(int val=1; val <= 5; ++val){
			try {
				RatingMapper.insert(val, lastId);
			} catch (RatingException e) {
				e.printStackTrace();
			}
		}
		//get ratings
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "GET", "/movies/" + lastId + "/ratings", "", ""});
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
		Response expected = new Response(HttpStatusCode.Ok, new ContentMovieRatings(movie), null);
//		Assert.assertEquals(expected.getContent(), res.getContent());
	}
}
