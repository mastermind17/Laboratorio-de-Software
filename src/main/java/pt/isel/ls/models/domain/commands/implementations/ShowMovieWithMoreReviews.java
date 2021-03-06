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
import pt.isel.ls.models.services.MoviesTopsQueries;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Collections;

/**
 * Show movie with most reviews
 * GET /tops/reviews/higher/count
 */
public class ShowMovieWithMoreReviews extends AbstractGetCommand {

	public ShowMovieWithMoreReviews() {
		pathTemplate = new PathTemplate("/tops/reviews/higher/count");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@Override
	public Response execute(Request req) {
		Movie movie;
		try {
			movie = MoviesTopsQueries.getMovieWithMoreReviews();

		} catch (SQLException ex) {
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		IContent resContent = movie == null ? new ContentText("No movie to show.") : new ContentMovie(movie);
		return new Response(HttpStatusCode.Ok, resContent, buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return "-> GET /tops/reviews/higher/count - returns the detail for the movie with most reviews.";
	}
}
