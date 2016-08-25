package pt.isel.ls.models.domain.movies;

public class Review {

	private int id;
	private String critic;
	private String summary;
	private String content;
	private int rating;

	public Review(int id, String critic, String summary, String content, int rating){
		this.id = id;
		this.critic = critic;
		this.summary = summary;
		this.content = content;
		this.rating  = rating;
	}

	/**
	 * Get the rating of this review.
	 * The string is represented with stars(*****).
	 * @return Rating value.
	 */
	public String getRatingRepresentation(){
		String out = "";
		for(int i=0; i < rating; ++i){
			out += "*";
		}
		return out;
	}

	@Override
	public String toString(){
		String ls = System.getProperty("line.separator");
		return new StringBuilder()
				.append("Summary: ").append(summary).append(ls)
				.append("Rating: ").append(rating).append(ls)
				.append("Author: ").append(critic).append(ls)
				.toString();
	}

	public int getId() { return id; }
	public int getRating() { return rating; }
	public String getCritic() { return critic; }
	public String getSummary() { return summary; }
	public String getContent() { return content; }
}
