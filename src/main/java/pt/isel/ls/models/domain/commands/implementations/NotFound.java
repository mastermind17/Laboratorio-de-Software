package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractBaseCommand;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.services.http.HttpStatusCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NotFound extends AbstractBaseCommand {

	private List<String> allowedMethods = Arrays.asList("GET", "POST", "LISTEN", "OPTION", "EXIT");

	public NotFound(){
		pathTemplate = new PathTemplate("/");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@Override
	public Response execute(Request req) {
		HttpStatusCode code;
		if(allowedMethods.contains(req.getMethod().getValue())){
			code = HttpStatusCode.NotFound;
			return new Response(code, new ContentError(new ResponseError(code, "Wrong path.")),
					buildResponseHeaders(req.getHeaders()));
		} else {
			code = HttpStatusCode.MethodNotAllowed;
			return new Response(code, new ContentError(new ResponseError(code, "Wrong method.")),
					buildResponseHeaders(req.getHeaders()));
		}
	}

	@Override
	public String toString(){
		return "-> Command not found.";
	}
}
