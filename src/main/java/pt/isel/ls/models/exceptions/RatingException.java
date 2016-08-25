package pt.isel.ls.models.exceptions;

public class RatingException extends Exception {

	private int rating;

	public RatingException(int rating){
		this.rating = rating;
	}

	public int getRating() { return rating; }
}
