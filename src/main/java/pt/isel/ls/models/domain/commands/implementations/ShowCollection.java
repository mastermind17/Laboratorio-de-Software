package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.movies.MovieCollection;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentCollection;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.mappers.CollectionMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Collections;

public class ShowCollection extends AbstractGetCommand {

	//messages
	public final static String COLLECTION_DOES_NOT_EXIST = "The given collection doesn't exist.";

	public ShowCollection(){
		pathTemplate = new PathTemplate("/collections/{cid}");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@Override
	public Response execute(Request req) {
		MovieCollection mc;
		int cid = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{cid}")));
		try {
			mc = CollectionMapper.get(cid);
		} catch(SQLException ex){
			HttpStatusCode code;
			if(ex.getErrorCode() == 0 && ex.getMessage().equals("The result set has no current row.")){
				code = HttpStatusCode.NotFound;
				return new Response(code, new ContentError(new ResponseError(code, COLLECTION_DOES_NOT_EXIST, ex)),
						buildResponseHeaders(req.getHeaders()));
			}
			code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		return new Response(HttpStatusCode.Ok, new ContentCollection(mc), buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return
				"GET /collections/{cid} - " +
				"returns the details for the cid collection, namely all the movies in that collection.";
	}
}
