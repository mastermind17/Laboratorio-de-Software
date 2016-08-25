package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.response.content.ContentHome;
import pt.isel.ls.services.http.HttpStatusCode;

import java.util.Collections;

public class Root extends AbstractGetCommand {

	public Root(){
		pathTemplate = new PathTemplate("/");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@Override
	public Response execute(Request req) {
		return new Response(HttpStatusCode.Ok, new ContentHome(), buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return "-> GET / - ContentHome.";
	}
}
