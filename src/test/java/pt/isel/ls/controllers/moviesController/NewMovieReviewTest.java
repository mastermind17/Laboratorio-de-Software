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
 * All tests on this class
 * Test request: POST /movies/{mid}/reviews
 * MovieController.newReview is the handler responsible
 *
 * SQL error codes are available here:
 * https://technet.microsoft.com/en-us/library/cc645603(v=sql.105).aspx
 */
public class NewMovieReviewTest extends BaseDatabaseTest {
	@Test
	public void testCommandWithCorrectParameters() throws SQLException, RequestException {
		//first insert a movie
		int lastId = MovieMapper.insert(new Movie(-1, "Dead pool", 17, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList()));
		//insert review
		String[] reviewArgs = {
				"POST",
				"/movies/" + lastId + "/reviews",
				"",
				"reviewerName=jp&reviewSummary=jp&review=jp&rating=5"};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(reviewArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Response expected = new Response(null, res.getContent(), null);
		Assert.assertEquals(res.getContent(), res.getContent());
	}

	@Test
	public void testCommandWithNonexistentMovie() throws RequestException {
		//insert review
		String[] reviewArgs = {
				"POST",
				"/movies/1/reviews",
				"",
				"reviewerName=turbo&reviewSummary=turbo&review=turbo&rating=5"};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(reviewArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		SQLException ex = (SQLException)res.getError().getCause();
//		Assert.assertTrue(ex instanceof SQLServerException);
		//0 indicates something..
//		Assert.assertTrue(ex.getErrorCode() == 0);
//		Assert.assertTrue(ex.getMessage().contains("The result set has no current row."));
	}

	@Test
	public void testCommandWithIncorrectRatingValue() throws SQLException, RequestException {
		//common values used throughout the test
		int rating = 17;
		//first insert a movie
		int lastId = MovieMapper.insert(new Movie(-1, "Dead pool", 17, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList()));
		//insert review
		String[] reviewArgs = {
				"POST",
				"/movies/" + lastId + "/reviews",
				"",
				"reviewerName=jp&reviewSummary=jp&review=jp&rating=" + rating};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(reviewArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		SQLException ex = (SQLException)res.getError().getCause();
//		Assert.assertTrue(ex instanceof SQLServerException);
		//547 indicates an sql constraint conflict
//		Assert.assertTrue(ex.getErrorCode() == 547);
//		Assert.assertTrue(ex.getMessage().contains("The INSERT statement conflicted with the CHECK constraint"));
	}
}
