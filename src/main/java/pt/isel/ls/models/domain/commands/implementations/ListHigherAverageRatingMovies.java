package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.*;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * List n movies with higher average ratings, sorted decreasingly
 * GET /tops/{n}/ratings/higher/average
 */
public class ListHigherAverageRatingMovies extends AbstractGetCommand {

	public ListHigherAverageRatingMovies() {
		pathTemplate = new PathTemplate("/tops/{n}/ratings/higher/average");
		ParameterRestrictions p1 = new ParameterRestrictions("top", false, Integer.class, null);
		ParameterRestrictions p2 = new ParameterRestrictions("skip", false, Integer.class, null);
		parametersTemplate = new ParametersTemplate(Arrays.asList(p1, p2));
	}

	@SuppressWarnings("Duplicates")
	@Override
	public Response execute(Request req) {
		List<Movie> movies;
		try {
			movies = MovieMapper.getAll();
		} catch (SQLException ex) {
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error occurred.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		movies.sort((m1, m2) -> m2.getAverageRating() - m1.getAverageRating());
		int n = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{n}")));
		n = n > movies.size() ? movies.size() : n;
		movies = movies.subList(0, n);
		movies = handlePaging(movies, req.getParameters());
		IContent resContent = movies.isEmpty() ?
				new ContentText("No movies to show.") : new ContentTopsRatingsMovies(movies, "Movies with better rating");
		return new Response(HttpStatusCode.Ok, resContent, buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public java.lang.String toString(){
		return "-> GET /tops/{n}/ratings/higher/average - " +
				"returns a list with the n movies with higher average ratings, sorted decreasingly.";
	}
}
