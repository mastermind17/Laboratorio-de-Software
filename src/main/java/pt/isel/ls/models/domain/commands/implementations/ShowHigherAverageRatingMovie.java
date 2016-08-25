package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentMovie;
import pt.isel.ls.models.domain.response.content.ContentText;
import pt.isel.ls.models.domain.response.content.IContent;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Show movie with the higher average rating
 * GET /tops/ratings/higher/average
 */
public class ShowHigherAverageRatingMovie extends AbstractGetCommand {

	public ShowHigherAverageRatingMovie() {
		pathTemplate = new PathTemplate("/tops/ratings/higher/average");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@SuppressWarnings("Duplicates")
	@Override
	public Response execute(Request req) {
		List<Movie> movies;
		try {
			movies = MovieMapper.getAll();
		} catch (SQLException ex) {
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		movies.sort( (m1, m2) -> m2.getAverageRating() - m1.getAverageRating() );
		IContent resContent = movies.isEmpty() ?
				new ContentText("No movie to show.") : new ContentMovie(movies.get(0));
		return new Response(HttpStatusCode.Ok, resContent, buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public java.lang.String toString(){
		return "-> GET /tops/ratings/higher/average - " +
				"returns the detail for the movie with the higher average rating.";
	}
}
