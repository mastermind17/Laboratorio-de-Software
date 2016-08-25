package pt.isel.ls.controllers.moviesController;

import org.junit.Test;
import pt.isel.ls.BaseDatabaseTest;
import pt.isel.ls.app.Dispatcher;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.content.ContentMovie;
import pt.isel.ls.models.exceptions.RequestException;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Collections;

/**
 * For all tests on this class
 * Test request: GET /movies/{mid}
 * ShowMovie is the command responsible
 *
 * SQL error codes are available here:
 * https://technet.microsoft.com/en-us/library/cc645603(v=sql.105).aspx
 */
public class ShowMovieTest extends BaseDatabaseTest {

	@Test
	public void testCommandWithCorrectParameters() throws SQLException, RequestException {
		pt.isel.ls.models.domain.movies.Movie expectedMovie = new pt.isel.ls.models.domain.movies.Movie(-1, "Mastermind", 17, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList());
		Response expected = new Response(HttpStatusCode.Ok, new ContentMovie(expectedMovie), null);
		//first insert a movie
		int lastId = MovieMapper.insert(new pt.isel.ls.models.domain.movies.Movie(-1, "Mastermind", 17, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList()));
		//get movie
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "GET", "/movies/" + lastId, "", ""});
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertEquals(expected.getContent(), res.getContent());
	}

	@Test
	public void testCommandWithNonexistentMovie() throws RequestException {
		//get movie
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "GET", "/movies/" + 17, "", ""});
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		SQLException ex = (SQLException)res.getError().getCause();
//		Assert.assertTrue(ex instanceof SQLServerException);
//		Assert.assertTrue(ex.getErrorCode() == 0);
//		Assert.assertEquals(ex.getMessage(), "The result set has no current row.");
	}
}
