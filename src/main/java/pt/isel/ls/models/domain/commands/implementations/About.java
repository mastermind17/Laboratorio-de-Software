package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.response.content.ContentAbout;
import pt.isel.ls.services.http.HttpStatusCode;

import java.util.Collections;

public class About extends AbstractGetCommand {

	public About(){
		pathTemplate = new PathTemplate("/about");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@Override
	public Response execute(Request req) {
		return new Response(HttpStatusCode.Ok, new ContentAbout(), buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return "-> GET /about - ContentAbout.";
	}
}
