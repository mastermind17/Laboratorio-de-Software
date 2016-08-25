package pt.isel.ls.models.domain.response;

import pt.isel.ls.services.http.HttpStatusCode;

/**
 * Representation of a Response error
 * Indicates that something went wrong
 * and the user required action couldn't didn't happen
 */
public class ResponseError {
	private HttpStatusCode statusCode;
	private String message;
	private Throwable exception;

	public ResponseError(HttpStatusCode statusCode, String message, Throwable exception){
		this.statusCode = statusCode;
		this.message = message;
		this.exception = exception;
	}

	public ResponseError(HttpStatusCode statusCode, String message){
		this(statusCode, message, null);
	}

	public String getMessage() { return message; }
	public Throwable getCause() { return exception; }
	public HttpStatusCode getStatusCode() { return statusCode; }
}
