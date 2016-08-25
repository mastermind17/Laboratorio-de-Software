package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractPostCommand;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentNumber;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Create new movie
 * POST /movies
 */
public class NewMovie extends AbstractPostCommand {

	public NewMovie(){
		pathTemplate = new PathTemplate("/movies");
		ParameterRestrictions p1 = new ParameterRestrictions("title", true, String.class, null);
		ParameterRestrictions p2 = new ParameterRestrictions("releaseYear", true, java.lang.Integer.class, null);
		parametersTemplate = new ParametersTemplate(Arrays.asList(p1, p2));
	}

	@Override
	public Response execute(Request req) {
		int autoId;
		try {
			Movie movie = new Movie(req.getParameters().get("title"),
					Integer.valueOf(req.getParameters().get("releaseYear")));
			autoId = MovieMapper.insert(movie);
		} catch(SQLException ex) {
			//check unique or primary key conflict
			if(ex.getErrorCode() == 2627){
				HttpStatusCode code = HttpStatusCode.BadRequest;
				return new Response(code, new ContentError(new ResponseError(code, "This movie already exists", ex)),
						buildResponseHeaders(req.getHeaders(), null));
			}
			//if its other cause
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders(), null));
		}
		return new Response(HttpStatusCode.SeeOther, new ContentNumber(autoId),
				buildResponseHeaders(req.getHeaders(), "/movies/" + autoId));
	}

	@Override
	public String toString(){
		String newLine = System.getProperty("line.separator");
		return
				"-> POST /movies - creates a new movie, given the following parameters" + newLine +
				"		* title - movie name" + newLine +
				"		* releaseYear - movie's release year." + newLine +
				"	This command returns the movie unique identifier.";
	}
}
