package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.movies.Review;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentReviewsSamples;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.models.mappers.ReviewMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ListMovieReviews extends AbstractGetCommand {

	//messages
	public final static String MOVIE_DOES_NOT_EXIST = "The given movie doesn't exist.";

	public ListMovieReviews() {
		pathTemplate = new PathTemplate("/movies/{mid}/reviews");
		ParameterRestrictions p1 = new ParameterRestrictions("top", false, Integer.class, null);
		ParameterRestrictions p2 = new ParameterRestrictions("skip", false, Integer.class, null);
		parametersTemplate = new ParametersTemplate(Arrays.asList(p1, p2));
	}

	@Override
	public Response execute(Request req) {
		int mid = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{mid}")));
		Movie movie;
		List<Review> reviews;
		try {
			movie = MovieMapper.get(mid);
			reviews = ReviewMapper.getAllOfMovie(mid);
		} catch(SQLException ex){
			if(ex.getErrorCode() == 0 && ex.getMessage().equals("The result set has no current row.")){
				HttpStatusCode code = HttpStatusCode.NotFound;
				return new Response(code, new ContentError(new ResponseError(code, MOVIE_DOES_NOT_EXIST, ex)),
						buildResponseHeaders(req.getHeaders()));
			}
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		reviews = handlePaging(reviews, req.getParameters());
		return new Response(HttpStatusCode.Ok,
				new ContentReviewsSamples(movie, reviews), buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return "-> GET /movies/{mid}/reviews - " +
				"returns all the reviews for the movie identified by mid. The information for each review " +
				"must not include the full review text.";
	}
}
