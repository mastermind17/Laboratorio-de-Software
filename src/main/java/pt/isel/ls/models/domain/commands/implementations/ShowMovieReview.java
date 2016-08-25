package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentReview;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.models.mappers.ReviewMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Collections;

/**
 * Show reviews of a movie
 * GET /movies/{mid}/reviews
 */
public class ShowMovieReview extends AbstractGetCommand {

	public ShowMovieReview(){
		pathTemplate = new PathTemplate("/movies/{mid}/reviews/{rid}");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	//TODO por esta formula do mid e rid num metodo auxiliar nas classes abstractas

	@Override
	public Response execute(Request req) {
		int mid = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{mid}")));
		int rid = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{rid}")));
		Movie movie;
		pt.isel.ls.models.domain.movies.Review review;
		try {
			movie = MovieMapper.get(mid);
			review = ReviewMapper.getOfMovie(mid, rid);
		} catch (SQLException ex) {
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		return new Response(HttpStatusCode.Ok,
				new ContentReview(movie, review),
				buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return
				"-> GET /movies/{mid}/reviews/{rid} - " +
				"returns the full information for the review rid of the movie identified by mid.";
	}
}
