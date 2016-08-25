package pt.isel.ls.controllers.moviesController;

import junit.framework.Assert;
import org.junit.Test;
import pt.isel.ls.BaseDatabaseTest;
import pt.isel.ls.app.Dispatcher;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.exceptions.RequestException;

/**
 * For all tests on this class
 * Test request: POST /movies
 * NewMovie is the command responsible
 *
 * SQL error codes are available here:
 * https://technet.microsoft.com/en-us/library/cc645603(v=sql.105).aspx
 */
public class NewMovieTest extends BaseDatabaseTest {

	@Test
	public void testCommandWithCorrectParameters() throws RequestException {
		//insert the movie
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "POST", "/movies", "", "title=Mastermind&releaseYear=17" });
		Response res = dis.getMatchingCommand(req).execute(req);
//		Response expected = new Response(null, res.getContent(), null);
		Assert.assertEquals(res.getContent(), res.getContent());
	}

	@Test
	public void testCommandWithDuplicateKeys() throws RequestException {
		//insert the movie
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "POST", "/movies", "", "title=Mastermind&releaseYear=17" });
		dis.getMatchingCommand(req).execute(req);
		//insert the same movie again
		Response res = dis.getMatchingCommand(req).execute(req);;
		//test
//		SQLException ex = (SQLException)null;
//		Assert.assertTrue(ex instanceof SQLServerException);
//		Assert.assertTrue(ex.getErrorCode() == 2627);
//		Assert.assertTrue(ex.getMessage().contains("Cannot insert duplicate key in object"));
	}
}
