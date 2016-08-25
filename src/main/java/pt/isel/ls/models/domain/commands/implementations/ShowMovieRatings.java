package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentMovieRatings;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Collections;

/**
 * Gets ratings information for the given movie
 * GET /movies/{mid}/ratings
 */
public class ShowMovieRatings extends AbstractGetCommand {

	//messages
	public final static String MOVIE_DOES_NOT_EXIST = "The given movie doesn't exist.";

	public ShowMovieRatings(){
		pathTemplate = new PathTemplate("/movies/{mid}/ratings");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@SuppressWarnings("Duplicates")
	@Override
	public Response execute(Request req) {
		int mid = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{mid}")));
		Movie movie;
		try {
			movie = MovieMapper.get(mid);
		} catch(SQLException ex){
			HttpStatusCode code;
			if(ex.getErrorCode() == 0 && ex.getMessage().equals("The result set has no current row.")){
				code = HttpStatusCode.NotFound;
				return new Response(code, new ContentError(new ResponseError(code, MOVIE_DOES_NOT_EXIST, ex)),
						buildResponseHeaders(req.getHeaders()));
			}
			code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		return new Response(HttpStatusCode.Ok, new ContentMovieRatings(movie), buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		String newLine = System.getProperty("line.separator");
		return
				"-> GET /movies/{mid}/ratings - " +
				"returns the rating information for the movie identified by mid. " +
				"This rating information include:" + newLine +
				"		* reviewerName - the reviewer name" + newLine +
				"		* reviewSummary - the review summary" + newLine +
				"		* review - the complete review" + newLine +
				"		* rating - the review rating" + newLine +
				"	This command returns the review unique identifier.";
	}
}
