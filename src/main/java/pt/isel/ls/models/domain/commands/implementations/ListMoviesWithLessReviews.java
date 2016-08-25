package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentText;
import pt.isel.ls.models.domain.response.content.ContentTopsRatingsMovies;
import pt.isel.ls.models.domain.response.content.IContent;
import pt.isel.ls.models.services.MoviesTopsQueries;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ListMoviesWithLessReviews extends AbstractGetCommand {

	public ListMoviesWithLessReviews() {
		pathTemplate = new PathTemplate("/tops/{n}/reviews/lower/count");
		ParameterRestrictions p1 = new ParameterRestrictions("top", false, Integer.class, null);
		ParameterRestrictions p2 = new ParameterRestrictions("skip", false, Integer.class, null);
		parametersTemplate = new ParametersTemplate(Arrays.asList(p1, p2));
	}

	@Override
	public Response execute(Request req) {
		int n = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{n}")));
		List<Movie> movies;
		try {
			movies = MoviesTopsQueries.getNMoviesWithLessReviews(n);
		} catch (SQLException ex) {
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		movies = handlePaging(movies, req.getParameters());
		IContent resContent = movies.isEmpty() ?
				new ContentText("No movies to show.") : new ContentTopsRatingsMovies(movies, "Movies with less reviews");
		return new Response(HttpStatusCode.Ok, resContent, buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return "-> GET /tops/{n}/reviews/lower/count - " +
				"returns a list with the n movies with lower review count";
	}
}
