package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.movies.MovieCollection;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentCollections;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.mappers.CollectionMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ListCollections extends AbstractGetCommand {

	public ListCollections() {
		pathTemplate = new PathTemplate("/collections");
		ParameterRestrictions p1 = new ParameterRestrictions("top", false, Integer.class, null);
		ParameterRestrictions p2 = new ParameterRestrictions("skip", false, Integer.class, null);
		parametersTemplate = new ParametersTemplate(Arrays.asList(p1, p2));
	}

	@Override
	public Response execute(Request req) {
		List<MovieCollection> mcs;
		try {
			mcs = CollectionMapper.getAll();
		} catch (SQLException ex){
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		mcs = handlePaging(mcs, req.getParameters());
		return new Response(HttpStatusCode.Ok, new ContentCollections(mcs), buildResponseHeaders(req.getHeaders()));
	}

	@Override
	public String toString(){
		return "-> GET /collections - returns the list of collections, using the insertion order.";
	}
}
