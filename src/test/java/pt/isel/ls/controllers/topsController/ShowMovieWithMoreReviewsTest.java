package pt.isel.ls.controllers.topsController;

import org.junit.Test;
import pt.isel.ls.BaseDatabaseTest;
import pt.isel.ls.app.Dispatcher;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.movies.Review;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.exceptions.RequestException;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.models.mappers.ReviewMapper;

import java.sql.SQLException;

/**
 * For all tests on this class
 * Test request: GET /tops/reviews/higher/count
 * ShowMovieWithMoreReviews is the command responsible.
 */
public class ShowMovieWithMoreReviewsTest extends BaseDatabaseTest {

	@Test
	public void testCommandWithMultipleMoviesAndMultipleReviews() throws SQLException, RequestException {
		//insert three movies
		Movie mov1 = new Movie("Garden of Sinners", 17);
		Movie mov2 = new Movie("Madoka", 12);
		int id1 = MovieMapper.insert(mov1);
		int id2 = MovieMapper.insert(mov2);
		MovieMapper.insert(new Movie("OP", 5));
		//insert reviews
		ReviewMapper.insert(new Review(-1, null, null, null, 5), id1);
		ReviewMapper.insert(new Review(-1, null, null, null, 5), id1);
		ReviewMapper.insert(new Review(-1, null, null, null, 5), id2);
		//execute handler
		String[] ratingArgs = {"GET", "/tops/reviews/higher/count", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertEquals(new ContentMovie(mov1).toPlainString(), res.getContent());
	}

	@Test
	public void testCommandWithOneMovieWithReviews() throws SQLException, RequestException {
		//insert one movie
		Movie mov1 = new Movie("Garden of Sinners", 17);
		int id1 = MovieMapper.insert(mov1);
		//insert reviews
		ReviewMapper.insert(new Review(-1, null, null, null, 5), id1);
		ReviewMapper.insert(new Review(-1, null, null, null, 5), id1);
		//execute handler
		String[] ratingArgs = {"GET", "/tops/reviews/higher/count", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertEquals(new ContentMovie(mov1).toPlainString(), res.getContent());
	}

	@Test
	public void testCommandWithOneMovieWithoutReviews() throws SQLException, RequestException {
		//insert one movie
		MovieMapper.insert(new Movie("OP", 5));
		//execute handler
		String[] ratingArgs = {"GET", "/tops/reviews/higher/count", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertTrue(res.getContent().equals(TopsController.NO_MOVIE_TO_SHOW));
//		Assert.assertEquals(res.getContent(), "");

	}


	@Test
	public void testCommandWithNonexistentMovie() throws RequestException {
		String[] ratingArgs = {"GET", "/tops/reviews/higher/count", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertTrue(res.getContent().equals(TopsController.NO_MOVIE_TO_SHOW));
//		Assert.assertEquals(res.getContent(), "");

	}
}
