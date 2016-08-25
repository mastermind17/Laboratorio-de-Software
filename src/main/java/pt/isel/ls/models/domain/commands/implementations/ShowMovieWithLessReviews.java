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

public class ShowMovieWithLessReviews extends AbstractGetCommand {

	public final static String NO_MOVIE_TO_SHOW = "No movie to show.";

	public ShowMovieWithLessReviews() {
		pathTemplate = new PathTemplate("/tops/reviews/lower/count");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@Override
	public Response execute(Request req) {
		Movie movie;
		try {
			movie = MoviesTopsQueries.getMovieWithLessReviews();
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
		return "-> GET /tops/reviews/lower/count - returns the detail for the movie with less reviews.";
	}
}
