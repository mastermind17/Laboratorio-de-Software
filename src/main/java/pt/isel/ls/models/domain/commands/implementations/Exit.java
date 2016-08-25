package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractBaseCommand;
import pt.isel.ls.models.domain.commands.components.MethodTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;

public class Exit extends AbstractBaseCommand {

	public Exit(){
		methodTemplate = new MethodTemplate("EXIT");
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
		System.exit(0);
		return null;
	}

	@Override
	public String toString(){
		return "-> EXIT / - ends the application.";
	}
}
