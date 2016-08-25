package pt.isel.ls.controllers.topsController;

import org.junit.Test;
import pt.isel.ls.BaseDatabaseTest;
import pt.isel.ls.app.Dispatcher;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.movies.Review;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.content.ContentMovies;
import pt.isel.ls.models.exceptions.RequestException;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.models.mappers.ReviewMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * For all tests on this class
 * Test request: GET /tops/{n}/reviews/higher/count
 * ListMoviesWithMoreReviews is the command responsible.
 */
public class ListMoviesWithMoreReviewsTest extends BaseDatabaseTest {

	@Test
	public void testCommandWithMultipleMoviesAndMultipleRatings() throws SQLException, RequestException {
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
		String[] ratingArgs = {"GET", "/tops/2/reviews/higher/count", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
		ArrayList<Movie> al = new ArrayList<>();
		al.add(mov1);
		al.add(mov2);
//		ArrayList al2 = (ArrayList)res.getContent();
		//TODO deixar de testar expected Responses e testar a content
//		Assert.assertEquals(res.getContent(), new ContentMovies(al).toPlainString());
	}

	@Test
	public void testCommandWithOneMovieWithRatings() throws SQLException, RequestException {
		//insert one movie
		Movie mov1 = new Movie("Garden of Sinners", 17);
		MovieMapper.insert(new Movie("OP", 5));
		int id1 = MovieMapper.insert(mov1);
		//insert reviews
		ReviewMapper.insert(new Review(-1, null, null, null, 5), id1);
		ReviewMapper.insert(new Review(-1, null, null, null, 5), id1);
		//execute handler
		String[] ratingArgs = {"GET", "/tops/1/reviews/higher/count", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
		ArrayList<Movie> al = new ArrayList<>();
		al.add(mov1);
		Response expected = new Response(HttpStatusCode.Ok, new ContentMovies(al), null);
		//TODO deixar de testar expected Responses e testar a content
//		Assert.assertEquals(expected.getContent(), res.getContent());
	}

	@Test
	public void testCommandWithOneMovieWithoutReviews() throws SQLException, RequestException {
		//insert one movie
		MovieMapper.insert(new Movie("OP", 5));
		//execute handler
		String[] ratingArgs = {"GET", "/tops/1/reviews/higher/count", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertTrue(res.getContent().equals(TopsController.NO_MOVIES_TO_SHOW));
//		Assert.assertEquals(res.getContent(), "");
	}

	@Test
	public void testCommandWithNonexistentMovie() throws RequestException {
		String[] ratingArgs = {"GET", "/tops/2/reviews/higher/count", "", ""};
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(ratingArgs);
		Response res = dis.getMatchingCommand(req).execute(req);
		//test
//		Assert.assertTrue(res.getContent().equals(TopsController.NO_MOVIES_TO_SHOW));
//		Assert.assertEquals(res.getContent(), "");
	}
}
