package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractBaseCommand;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.services.http.HttpStatusCode;

import java.util.Collections;

public class MalformedRequest extends AbstractBaseCommand {

	private String message;

	public MalformedRequest(String message){
		this.message = message;
		pathTemplate = new PathTemplate("/");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@Override
	public boolean matches(Request req) { return true; }

	@Override
	public boolean validateParameters(Request req) { return true; }

	@Override
	public boolean validateHeaders(Request req) { return true; }

	@Override
	public Response execute(Request req) {
		HttpStatusCode code = HttpStatusCode.BadRequest;
		return new Response(code, new ContentError(new ResponseError(code, message)),
				buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return "-> Malformed Request.";
	}
}
