package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractPostCommand;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.movies.MovieCollection;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentNumber;
import pt.isel.ls.models.mappers.CollectionMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Arrays;

public class NewCollection extends AbstractPostCommand {

	public NewCollection(){
		pathTemplate = new PathTemplate("/collections");
		ParameterRestrictions p1 = new ParameterRestrictions("name", true, String.class, null);
		ParameterRestrictions p2 = new ParameterRestrictions("description", true, String.class, null);
		parametersTemplate = new ParametersTemplate(Arrays.asList(p1, p2));
	}

	@Override
	public Response execute(Request req) {
		int autoId;
		try {
			MovieCollection mc = new MovieCollection(
					-1,
					req.getParameters().get("name"),
					req.getParameters().get("description"),
					null);
			autoId = CollectionMapper.insertCollection(mc);
		} catch (SQLException ex) {
			//if its other cause
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders(), null));
		}
		return new Response(HttpStatusCode.SeeOther, new ContentNumber(autoId),
				buildResponseHeaders(req.getHeaders(), "/collections/" + autoId));
	}

	@Override
	public String toString(){
		String newLine = System.getProperty("line.separator");
		return
				"-> POST /collections - " +
				"creates a new collection and returns its identifier, given the following parameters" + newLine +
				"		* name - the tag unique name" + newLine +
				"		* description - the tag description";
	}
}
