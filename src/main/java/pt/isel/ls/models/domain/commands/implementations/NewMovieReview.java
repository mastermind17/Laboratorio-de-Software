package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractPostCommand;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.movies.Review;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.*;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentNumber;
import pt.isel.ls.models.mappers.ReviewMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Create new review
 * POST /movies/{mid}/reviews
 */
public class NewMovieReview extends AbstractPostCommand {

	//messages
	public final static String MOVIE_DOES_NOT_EXIST = "The given movie doesn't exist.";

	public NewMovieReview(){
		pathTemplate = new PathTemplate("/movies/{mid}/reviews");
		ParameterRestrictions p1 = new ParameterRestrictions("reviewerName", true, String.class, null);
		ParameterRestrictions p2 = new ParameterRestrictions("reviewSummary", true, String.class, null);
		ParameterRestrictions p3 = new ParameterRestrictions("review", true, String.class, null);
		ParameterRestrictions p4 = new ParameterRestrictions("rating", true, java.lang.Integer.class, null);
		parametersTemplate = new ParametersTemplate(Arrays.asList(p1, p2, p3, p4));
	}

	@Override
	public Response execute(Request req) {
		int rating = Integer.valueOf(req.getParameters().get("rating"));
		int mid = Integer.valueOf(req.getPath().get(pathTemplate.indexOf("{mid}")));
		int autoId;
		try {
			Review rev = new Review(
					-1,
					req.getParameters().get("reviewerName"),
					req.getParameters().get("reviewSummary"),
					req.getParameters().get("review"),
					rating);
			autoId = ReviewMapper.insert(rev, mid);
		} catch(SQLException ex) {
			HttpStatusCode code;
			//incorrect rating value
			if(ex.getErrorCode() == 547 &&
					ex.getMessage().contains("The INSERT statement conflicted with the CHECK constraint")){
				code = HttpStatusCode.BadRequest;
				return new Response(code,
						new ContentError(new ResponseError(code, "This rating isn't allowed: " + rating, ex)),
						buildResponseHeaders(req.getHeaders(), null));
			}
			//nonexistent movie
			if(ex.getErrorCode() == 0 && ex.getMessage().equals("The result set has no current row.")){
				code = HttpStatusCode.BadRequest;
				return new Response(code, new ContentError(new ResponseError(code, MOVIE_DOES_NOT_EXIST, ex)),
						buildResponseHeaders(req.getHeaders(), null));
			}
			//if its other cause
			code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders(), null));
		}
		return new Response(HttpStatusCode.SeeOther, new ContentNumber(autoId),
				buildResponseHeaders(req.getHeaders(), "/movies/" + mid + "/reviews/" + autoId));
	}

	@Override
	public String toString(){
		String newLine = System.getProperty("line.separator");
		return
				"-> POST /movies/{mid}/reviews - " +
				"creates a new review for the movie identified by mid, given the following parameters" + newLine +
				"		* reviewerName - the reviewer name" + newLine +
				"		* reviewSummary - the review summary" + newLine +
				"		* review - the complete review" + newLine +
				"		* rating - the review rating" + newLine +
				"	This command returns the review unique identifier.";
	}
}
