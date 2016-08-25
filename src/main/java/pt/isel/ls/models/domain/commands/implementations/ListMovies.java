package pt.isel.ls.models.domain.commands.implementations;

import pt.isel.ls.models.domain.commands.AbstractGetCommand;
import pt.isel.ls.models.domain.commands.components.ParameterRestrictions;
import pt.isel.ls.models.domain.commands.components.ParametersTemplate;
import pt.isel.ls.models.domain.commands.components.PathTemplate;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.response.Response;
import pt.isel.ls.models.domain.response.ResponseError;
import pt.isel.ls.models.domain.response.content.ContentError;
import pt.isel.ls.models.domain.response.content.ContentMovies;
import pt.isel.ls.models.mappers.MovieMapper;
import pt.isel.ls.services.http.HttpStatusCode;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Show all movies
 * GET /movies
 */
public class ListMovies extends AbstractGetCommand {

	public ListMovies() {
		pathTemplate = new PathTemplate("/movies");
		ParameterRestrictions p1 = new ParameterRestrictions("top", false, Integer.class, null);
		ParameterRestrictions p2 = new ParameterRestrictions("skip", false, Integer.class, null);
		ParameterRestrictions p3 = new ParameterRestrictions("sortBy", false, String.class,
				Arrays.asList("addedDate","addedDateDesc","year","yearDesc","title","titleDesc","rating","ratingDesc"));
		parametersTemplate = new ParametersTemplate(Arrays.asList(p1, p2, p3));
	}

	@Override
	public Response execute(Request req){
		List<Movie> movies;
		try {
			movies = MovieMapper.getAll();
		} catch (SQLException ex){
			HttpStatusCode code = HttpStatusCode.InternalServerError;
			return new Response(code, new ContentError(new ResponseError(code, "Unexpected sql error.", ex)),
					buildResponseHeaders(req.getHeaders()));
		}
		if(req.getParameters().containsKey("sortBy"))
			order(movies, req.getParameters().get("sortBy"));
		movies = handlePaging(movies, req.getParameters());
		return new Response(HttpStatusCode.Ok, new ContentMovies(movies), buildResponseHeaders(req.getHeaders()));
	}

	private List<Movie> order(List<Movie> movies, String param){
		Comparator<Movie> cmp;
		// addedDateEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
		switch(param){
			case "addedDate": cmp = (a, b) -> a.getId() - b.getId(); 						break;
			case "addedDateDesc": cmp = (a, b) -> b.getId() - a.getId(); 					break;
			case "year": cmp = (a, b) -> a.getReleaseYear() - b.getReleaseYear();			break;
			case "yearDesc": cmp = (a, b) -> b.getReleaseYear() - a.getReleaseYear(); 		break;
			case "title": cmp = (a, b) -> a.getTitle().compareTo(b.getTitle()); 			break;
			case "titleDesc": cmp = (a, b) -> b.getTitle().compareTo(a.getTitle()); 		break;
			case "rating": cmp = (a, b) -> b.getAverageRating() - a.getAverageRating(); 	break;
			case "ratingDesc": cmp = (a, b) -> a.getAverageRating() - b.getAverageRating(); break;
			default: cmp =  (a, b) -> a.getTitle().compareTo(b.getTitle()); 				break;
		}
		movies.sort(cmp);
		return movies;
	}

	@Override
	public String toString(){
		return "-> GET /movies - returns a list with all movies.";
	}
}
