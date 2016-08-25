package pt.isel.ls.controllers.moviesController;

import org.junit.Test;
import pt.isel.ls.BaseDatabaseTest;
import pt.isel.ls.app.Dispatcher;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.response.content.ContentMovies;
import pt.isel.ls.models.exceptions.RequestException;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * For all tests on this class
 * Test request: GET /movies
 * ListMovies is the command responsible
 *
 * SQL error codes are available here:
 * https://technet.microsoft.com/en-us/library/cc645603(v=sql.105).aspx
 */
public class ListMoviesTest extends BaseDatabaseTest {

	@Test
	public void testCommandWithoutMovies() throws RequestException {
//		Response expected = new Response(null, new ContentMovie[0], null);
		Response expected = new Response(HttpStatusCode.Ok, new ContentMovies(new ArrayList<>()), null);
		//get all movies
		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "GET", "/movies", "", ""});
		Response res = dis.getMatchingCommand(req).execute(req);

		//
//		Assert.assertEquals(expected, res);
//		Assert.assertEquals("", res.getContent());
	}

	@Test
	public void testCommandWithOneMovie() throws SQLException, RequestException {
		Movie m1 = new Movie(-1, "Mastermind", 17, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList());
		ArrayList<Movie> ms = new ArrayList<>();
		ms.add(m1);
		Response expected = new Response(HttpStatusCode.Ok, new ContentMovies(ms), null);
		//first insert the movie
		MovieMapper.insert(m1);
		//get all movies

		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "GET", "/movies", "", ""});
		Response res = dis.getMatchingCommand(req).execute(req);

		//test
//		Assert.assertEquals(expected.getContent(), res.getContent());
	}

	@Test
	public void testCommandWithMultipleMovies() throws SQLException, RequestException {
		Movie m1 = new Movie(-1, "Mastermind", 17, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList());
		Movie m2 = new Movie(-1, "Paprika", 12, null, null, null, null,new int[]{0,0,0,0,0}, Collections.emptyList());
		ArrayList<Movie> ms = new ArrayList<>();
		ms.add(m1);
		ms.add(m2);
		Response expected = new Response(HttpStatusCode.Ok, new ContentMovies(ms), null);
		//first insert the movies
		MovieMapper.insert(m1);
		MovieMapper.insert(m2);
		//get all movies

		Dispatcher dis = new Dispatcher();
		Request req = Request.valueOf(new String[] { "GET", "/movies", "", ""});
		Response res = dis.getMatchingCommand(req).execute(req);

		//test
//		Assert.assertEquals(expected.getContent(), res.getContent());
	}
}
