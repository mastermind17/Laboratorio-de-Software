package pt.isel.ls.models.domain.commands;

import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;

public interface ICommand {

	boolean matches(Request req);

	boolean validateParameters(Request req);

	boolean validateHeaders(Request req);

	Response execute(Request req);
}
