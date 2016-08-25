package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentMovie;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.services.http.HttpStatusCode;
import pt.isel.ls.utils.Utils;

import java.sql.SQLException;
import java.util.Collections;

/**
 * Show movie detail
 * GET /movies/{mid}
 */
public class ShowMovie extends AbstractGetCommand {

	//messages
	public final static String MOVIE_DOES_NOT_EXIST = "The given movie doesn't exist.";

	public ShowMovie(){
		pathTemplate = new PathTemplate("/movies/{mid}");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@SuppressWarnings("Duplicates")
	@Override
	public Response execute(Request req) {
		pt.isel.ls.models.domain.movies.Movie movie;
		String i = req.getPath().get(pathTemplate.indexOf("{mid}"));
		if(!Utils.isInteger(i))
			return new Response(HttpStatusCode.NotFound, new ContentError(new ResponseError(HttpStatusCode.NotFound, "Filme não existe")), buildResponseHeaders(req.getHeaders()));

		int mid = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{mid}")));
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
		return new Response(HttpStatusCode.Ok, new ContentMovie(movie), buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return "-> GET /movies/{mid} - returns the detailed information for the movie identified by mid.";
	}
}
