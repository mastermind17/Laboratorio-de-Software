package pt.isel.ls.controllers.topsController;

import org.junit.Test;
import pt.isel.ls.BaseDatabaseTest;
import pt.isel.ls.app.Dispatcher;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.exceptions.RatingException;
import pt.isel.ls.models.exceptions.RequestException;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.models.mappers.RatingMapper;

import java.sql.SQLException;

/**
 * For all tests on this class
 * Test request: GET /tops/ratings/lower/average
 * ShowLowerAverageRatingMovie is the command responsible.
 */
public class ShowLowerAverageRatingMovieTest extends BaseDatabaseTest {

	@Test
	public void testCommandWithMultipleMoviesAndMultipleRatings() throws SQLException, RequestException {
		//insert three movies
		Movie mov1 = new Movie("Kappa", 17);
		Movie mov2 = new Movie("Matrix", 12);
		int id1 = MovieMapper.insert(mov1);
		int id2 = MovieMapper.insert(mov2);
		MovieMapper.insert(new Movie("OP", 5));
		//insert ratings
		try {
			RatingMapper.insert(5, id1);
			RatingMapper.insert(1, id1);
			RatingMapper.insert(2, id2);
		} catch (RatingException e) {
			e.printStackTrace();
		}
		//execute handler
		String[] ratingArgs = {"GET", "/tops/ratings/lower/average", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertEquals(new ContentMovie(mov2).toPlainString(), res.getContent());
	}

	@Test
	public void testCommandWithOneMovieWithMultipleRatings() throws SQLException, RequestException {
		//insert one movies
		Movie mov1 = new Movie("GOT", 5);
		int id1 = MovieMapper.insert(mov1);
		//insert ratings
		try {
			RatingMapper.insert(5, id1);
			RatingMapper.insert(5, id1);
		} catch (RatingException e) {
			e.printStackTrace();
		}
		//execute handler
		String[] ratingArgs = {"GET", "/tops/ratings/lower/average", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertEquals(new ContentMovie(mov1).toPlainString(), res.getContent());
	}


	@Test
	public void testCommandWithOneMovieWithoutRatings() throws SQLException, RequestException {
		//insert three movies
		MovieMapper.insert(new Movie("OP", 5));
		//execute handler
		String[] ratingArgs = {"GET", "/tops/ratings/lower/average", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertTrue(res.getContent().equals(TopsController.NO_MOVIE_TO_SHOW));
//		Assert.assertEquals(res.getContent(), "");

	}

	@Test
	public void testCommandWithNonexistentMovie() throws RequestException {
		String[] ratingArgs = {"GET", "/tops/ratings/lower/average", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertTrue(res.getContent().equals(TopsController.NO_MOVIE_TO_SHOW));
//		Assert.assertEquals(res.getContent(), "");

	}
}
