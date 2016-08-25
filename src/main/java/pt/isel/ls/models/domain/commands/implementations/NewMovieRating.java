package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.commands.AbstractPostCommand;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentText;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.exceptions.RatingException;
import pt.isel.ls.models.mappers.RatingMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Collections;

/**
 * Create new rating
 * POST /movies/{mid}/ratings
 */
public class NewMovieRating extends AbstractPostCommand {

	//messages
	public final static java.lang.String MOVIE_DOES_NOT_EXIST = "The given movie doesn't exist.";

	public NewMovieRating(){
		pathTemplate = new PathTemplate("/movies/{mid}/ratings");
		ParameterRestrictions p1 = new ParameterRestrictions("rating", true, Integer.class, null);
		parametersTemplate = new ParametersTemplate(Collections.singletonList(p1));
	}

	@Override
	public Response execute(Request req) {
		int mid = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{mid}")));
		int rating = Integer.valueOf(req.getParameters().get("rating"));
		try {
			RatingMapper.insert(rating, mid);
		} catch(SQLException ex) {
			HttpStatusCode code;
			//foreign key conflict
			if(ex.getErrorCode() == 547 &&
					ex.getMessage().contains("The INSERT statement conflicted with the FOREIGN KEY constraint")){
				code = HttpStatusCode.BadRequest;
				return new Response(code,
						new ContentError(new ResponseError(code, MOVIE_DOES_NOT_EXIST, ex)),
						buildResponseHeaders(req.getHeaders(), null));
			}
			//if its other cause
			code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders(), null));
		} catch (RatingException ex) {
			//incorrect rating value
			HttpStatusCode code = HttpStatusCode.BadRequest;
			return new Response(code,
					new ContentError(new ResponseError(code, "Rating not allowed: " + rating, ex)),
					buildResponseHeaders(req.getHeaders(), null));
		}
		return new Response(HttpStatusCode.SeeOther, new ContentText(""),
				buildResponseHeaders(req.getHeaders(), "/movies/" + mid + "/ratings"));
	}

	@Override
	public java.lang.String toString(){
		String newLine = System.getProperty("line.separator");
		return
				"-> POST /movies/{mid}/ratings - " +
				"submits a new rating for the movie identified by mid, given the following parameters" + newLine +
				"		* rating - integer between 1 and 5.";
	}
}
