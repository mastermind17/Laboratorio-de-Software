package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.response.content.ContentTopsRatings;
import pt.isel.ls.services.http.HttpStatusCode;

import java.util.Collections;

public class TopsRatings extends AbstractGetCommand {

	public TopsRatings(){
		pathTemplate = new PathTemplate("/tops/ratings");
		parametersTemplate = new ParametersTemplate(Collections.emptyList());
	}

	@Override
	public Response execute(Request req) {
		return new Response(HttpStatusCode.Ok, new ContentTopsRatings(), buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return "-> GET /tops/ratings - Tops.";
	}
}
