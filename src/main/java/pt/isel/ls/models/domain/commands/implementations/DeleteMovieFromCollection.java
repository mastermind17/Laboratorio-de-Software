package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractBaseCommand;
import pt.isel.ls.models.domain.commands.components.HeadersTemplate;
import pt.isel.ls.models.domain.commands.components.MethodTemplate;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentText;
import pt.isel.ls.models.mappers.CollectionMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Collections;

public class DeleteMovieFromCollection extends AbstractBaseCommand {

	public DeleteMovieFromCollection(){
		methodTemplate = new MethodTemplate("DELETE");
		pathTemplate = new PathTemplate("/collections/{cid}/movies/{mid}");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
		headersTemplate = new HeadersTemplate(Collections.emptyList());
	}

	@Override
	public Response execute(Request req) {
		try {
			CollectionMapper.deleteMovieFromCollection(
					Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{mid}"))),
					Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{cid}"))));
		} catch (SQLException ex) {
			//if its other cause
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		return new Response(HttpStatusCode.Ok, new ContentText(""), buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public java.lang.String toString(){
		return
				"-> DELETE /collections/{cid}/movies/{mid} - " +
				"removes the movie mid from the collections cid.";
	}
}