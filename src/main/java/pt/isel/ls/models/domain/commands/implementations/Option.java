package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.app.Dispatcher;
import pt.isel.ls.models.domain.commands.AbstractBaseCommand;
import pt.isel.ls.models.domain.commands.components.MethodTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.content.ContentText;
import pt.isel.ls.services.http.HttpStatusCode;

public class Option extends AbstractBaseCommand {

	public Option(){
		methodTemplate = new MethodTemplate("OPTION");
		pathTemplate = new PathTemplate("/");
	}

	@Override
	public boolean validateParameters(Request req){
		return true;
	}

	@Override
	public boolean validateHeaders(Request req){
		return true;
	}


	@Override
	public Response execute(Request req) {
		Dispatcher dis = new Dispatcher();
		String[] str = {""};
		dis.getCommands().forEach( cmd -> str[0]+= cmd.toString() + System.getProperty("line.separator"));
		return new Response(HttpStatusCode.Ok, new ContentText(str[0]), buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public java.lang.String toString(){
		return "-> OPTION / - presents a list of available commands and their characteristics.";
	}
}
