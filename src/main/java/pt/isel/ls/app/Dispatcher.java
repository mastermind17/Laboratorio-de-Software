package pt.isel.ls.app;

import pt.isel.ls.models.domain.request.Request;
import pt.isel.ls.models.domain.commands.ICommand;
import pt.isel.ls.models.domain.commands.implementations.*;

import java.util.*;

/**
 * Container of all the commands available.
 */
public class Dispatcher {

	private List<ICommand> commands;
	public List<ICommand> getCommands() { return commands; }

	public Dispatcher(){
		commands = new ArrayList<>();
		commands.add(new Exit());
		commands.add(new Option());
		commands.add(new ShowMovie());
		commands.add(new ListMovies());
		commands.add(new NewMovie());
		commands.add(new NewMovieRating());
		commands.add(new ShowMovieRatings());
		commands.add(new NewMovieReview());
		commands.add(new ListMovieReviews());
		commands.add(new ShowMovieReview());
		commands.add(new ShowHigherAverageRatingMovie());
		commands.add(new ListHigherAverageRatingMovies());
		commands.add(new ShowLowerAverageRatingMovie());
		commands.add(new ListLowerAverageRatingMovies());
		commands.add(new ShowMovieWithMoreReviews());
		commands.add(new ShowMovieWithLessReviews());
		commands.add(new ListMoviesWithMoreReviews());
		commands.add(new ListMoviesWithLessReviews());
		commands.add(new NewCollection());
		commands.add(new ListCollections());
		commands.add(new ShowCollection());
		commands.add(new AddMovieToCollection());
		commands.add(new DeleteMovieFromCollection());
		commands.add(new Listen());
		commands.add(new Root());
		commands.add(new About());
		commands.add(new TopsRatings());
	}

	public ICommand getMatchingCommand(Request req){
		for(ICommand cmd : commands){
			// check method and path
			if(cmd.matches(req)) {
				// check parameters
				if(!cmd.validateParameters(req))
					return new MalformedRequest("Malformed parameters.");
				// check headers
				if(!cmd.validateHeaders(req))
					return new MalformedRequest("Malformed headers.");
				// valid command found
				return cmd;
			}
		}
		return new NotFound();
	}
}
