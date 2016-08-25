package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractPostCommand;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentNumber;
import pt.isel.ls.models.mappers.CollectionMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Collections;

public class AddMovieToCollection extends AbstractPostCommand {

	//messages
	public final static java.lang.String MOVIE_DOES_NOT_EXIST = "The given movie doesn't exist.";

	public AddMovieToCollection(){
		pathTemplate = new PathTemplate("/collections/{cid}/movies/");
		ParameterRestrictions p1 = new ParameterRestrictions("mid", true, Integer.class, null);
		parametersTemplate = new ParametersTemplate(Collections.singletonList(p1));
	}

	@Override
	public Response execute(Request req) {
		int autoId;
		int cid = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{cid}")));
		try {
			autoId = CollectionMapper.insertMovieInCollection(
					Integer.valueOf(req.getParameters().get("mid")),
					cid);
		} catch (SQLException ex) {
			HttpStatusCode code = HttpStatusCode.BadRequest;
			if(ex.getErrorCode() == 2627 && ex.getMessage().contains("Violation of UNIQUE KEY")){
				ResponseError error = new ResponseError(code, "Movie is already on the collection.", ex);
				return new Response(code, new ContentError(error), buildResponseHeaders(req.getHeaders(), null));
			}
			if(ex.getErrorCode() == 547){
				ResponseError error = new ResponseError(code, "Movie or collection doesn't exist.", ex);
				return new Response(code, new ContentError(error), buildResponseHeaders(req.getHeaders(), null));
			}
			//if its other cause
			code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders(), null));
		}
		return new Response(HttpStatusCode.SeeOther,
				new ContentNumber(autoId),
				buildResponseHeaders(req.getHeaders(), "/collections/" + cid + "/"));
	}

	@Override
	public java.lang.String toString(){
		return "-> POST /collections/{cid}/movies/ - adds a movie to the cid collection, given" +
				"		* mid - the movie unique identifier.";
	}

}
