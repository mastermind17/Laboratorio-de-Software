package pt.isel.ls.models.domain.commands;

import pt.isel.ls.models.domain.commands.components.HeadersTemplate;
import pt.isel.ls.models.domain.commands.components.MethodTemplate;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.request.components.Headers;

import java.util.HashMap;

/**
 * Base command implementation.
 */
public abstract class AbstractBaseCommand implements ICommand {

	protected MethodTemplate methodTemplate;
	protected PathTemplate pathTemplate;
	protected HeadersTemplate headersTemplate;
	protected ParametersTemplate parametersTemplate;

	@Override
	public boolean matches(Request req) {
		return methodTemplate.validates(req.getMethod()) && pathTemplate.validates(req.getPath());
	}

	@Override
	public boolean validateParameters(Request req){
		return parametersTemplate.validates(req.getParameters());
	}

	@Override
	public boolean validateHeaders(Request req){
		return headersTemplate.validates(req.getHeaders());
	}

	/**
	 * Given a Request headers builds the corresponding Response headers.
	 * @param reqHeaders Request headers.
	 * @return Response Headers.
	 */
	protected HashMap<String,String> buildResponseHeaders(Headers reqHeaders){
		HashMap<String,String> resHeaders = new HashMap<>();
		// Content-Type
		if(reqHeaders.containsKey("accept"))
			resHeaders.put("Content-Type", reqHeaders.get("accept").get(0));
		else
			resHeaders.put("Content-Type", "text/plain");
		// file-name
		if(reqHeaders.containsKey("file-name"))
			resHeaders.put("file-name", reqHeaders.get("file-name").get(0));
		return resHeaders;
	}

	public PathTemplate getPathTemplate() { return pathTemplate; }
	public MethodTemplate getMethodTemplate() { return methodTemplate; }
	public HeadersTemplate getHeadersTemplate() { return headersTemplate; }
	public ParametersTemplate getParametersTemplate() { return parametersTemplate; }
}
