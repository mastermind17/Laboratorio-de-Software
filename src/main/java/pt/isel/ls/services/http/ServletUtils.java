package pt.isel.ls.services.http;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ServletUtils {

	/**
	 * Get an HttpServletRequest headers as a map.
	 * @param request HttpServletRequest
	 * @return Map
	 */
	public static Map<String, List<String>> getHeaders(HttpServletRequest request) {
		Map<String, List<String>> map = new HashMap<>();
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			List<String> list = Collections.list(request.getHeaders(key));
			// yolooo
			List<String> values = Arrays.asList(list.get(0).split(","));
			map.put(key, values);
		}
		return map;
	}
}
