package pt.isel.ls.models.domain.request;

import pt.isel.ls.models.domain.request.components.Headers;
import pt.isel.ls.models.domain.request.components.Method;
import pt.isel.ls.models.domain.request.components.Parameters;
import pt.isel.ls.models.domain.request.components.Path;
import pt.isel.ls.models.exceptions.RequestException;
import pt.isel.ls.services.http.ServletUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Representation of a user action
 */
public class Request {

	private Method method;
	private Path path;
	private Headers headers;
	private Parameters parameters;

	public Request(Method method, Path path, Headers headers, Parameters parameters){
		this.method = method;
		this.path = path;
		this.headers = headers;
		this.parameters = parameters;
	}

	public Path getPath() { return path; }
	public Method getMethod() { return method; }
	public Headers getHeaders() { return headers; }
	public Parameters getParameters() { return parameters; }

	public static Request valueOf(HttpServletRequest req){
		return new Request(
				new Method(req.getMethod()),
				new Path(req.getPathInfo()),
				new Headers(ServletUtils.getHeaders(req)),
				new Parameters(req.getParameterMap())
		);
	}

	public static Request valueOf(String[] input) throws RequestException {
		String[] args = new String[4];
		System.arraycopy(input, 0, args, 0, input.length);
		for (int i=input.length; i < args.length; ++i)
			args[i] = "";
		if(args[2].contains("=") && args[2].contains(":"))
			throw new RequestException();
		if(args[3].contains("=") && args[3].contains(":"))
			throw new RequestException();
		// if parameters are present and no headers
		if(args[3].equals("") && args[2].contains("=")){
			return new Request(new Method(args[0].toUpperCase()), new Path(args[1]),
					new Headers(""), new Parameters(args[2]));
		}
		return new Request(new Method(args[0].toUpperCase()), new Path(args[1]),
				new Headers(args[2]), new Parameters(args[3]));
	}
}
