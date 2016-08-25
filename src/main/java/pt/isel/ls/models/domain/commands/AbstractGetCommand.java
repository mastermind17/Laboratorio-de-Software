package pt.isel.ls.models.domain.commands;

import pt.isel.ls.models.domain.commands.components.*;
import pt.isel.ls.models.domain.request.components.Headers;
import pt.isel.ls.models.domain.request.components.Parameters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Base implementation for all GET commands.
 */
public abstract class AbstractGetCommand extends AbstractBaseCommand {

	public AbstractGetCommand(){
		methodTemplate = new MethodTemplate("GET");
		headersTemplate = getDefaultHeaders();
	}

	private HeadersTemplate getDefaultHeaders(){
		HeaderRestrictions h1 = new HeaderRestrictions("accept", false, "text/plain", String.class, Arrays.asList("text/plain", "text/html"));
		HeaderRestrictions h2 = new HeaderRestrictions("file-name", false, null, String.class, null);
		return new HeadersTemplate(Arrays.asList(h1, h2));
	}

	/**
	 * Given a Request headers builds the corresponding Response headers.
	 * @param reqHeaders Request headers.
	 * @return Response Headers.
	 */
	@Override
	protected HashMap<String,String> buildResponseHeaders(Headers reqHeaders){
		HashMap<String,String> resHeaders = new HashMap<>();
		// Content-Type
		if(reqHeaders.containsKey("accept"))
			resHeaders.put("Content-Type", reqHeaders.get("accept").get(0));
		else
			resHeaders.put("Content-Type", "text/html");
		// file-name
		if(reqHeaders.containsKey("file-name"))
			resHeaders.put("file-name", reqHeaders.get("file-name").get(0));
		return resHeaders;
	}

	/**
	 * Utility method to handle paging if top and skip parameters are present ensuring the indexes
	 * don't exceed the dimension.
	 * @param list List.
	 * @param parameters Request parameters.
	 * @param <T> Generic parameter.
	 * @return Paging result.
	 */
	protected <T> List<T> handlePaging(List<T> list, Parameters parameters){
		int fromIdx = parameters.containsKey("skip") ? Integer.valueOf(parameters.get("skip")) : 0;
		if(fromIdx > list.size())
			fromIdx = list.size();
		int toIdx = parameters.containsKey("top") ?
				fromIdx + Integer.valueOf(parameters.get("top")) : list.size();
		if(toIdx > list.size())
			toIdx = list.size();
		return list.subList(fromIdx, toIdx);
	}
}
