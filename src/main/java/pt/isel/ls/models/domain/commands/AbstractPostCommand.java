package pt.isel.ls.models.domain.commands;

import pt.isel.ls.models.domain.commands.components.HeadersTemplate;
import pt.isel.ls.models.domain.commands.components.MethodTemplate;
import pt.isel.ls.models.domain.request.components.Headers;

import java.util.Collections;
import java.util.HashMap;

/**
 * Base implementation for all POST commands.
 */
public abstract class AbstractPostCommand extends AbstractBaseCommand {

	protected AbstractPostCommand(){
		methodTemplate = new MethodTemplate("POST");
		headersTemplate = new HeadersTemplate(Collections.emptyList());
	}

	/**
	 * Given a Request headers builds the corresponding Response headers.
	 * @param reqHeaders Request headers.
	 * @return Response Headers.
	 */
	protected HashMap<String,String> buildResponseHeaders(Headers reqHeaders, String location){
		HashMap<String,String> resHeaders = new HashMap<>();
		// Content-Type
		if(reqHeaders.containsKey("accept"))
			resHeaders.put("Content-Type", reqHeaders.get("accept").get(0));
		else
			resHeaders.put("Content-Type", "text/html");
		// file-name
		if(reqHeaders.containsKey("file-name"))
			resHeaders.put("file-name", reqHeaders.get("file-name").get(0));
		// Location
//		resHeaders.put("Content-Type", "text/plain");
		resHeaders.put("Location", location);
		return resHeaders;
	}
}
