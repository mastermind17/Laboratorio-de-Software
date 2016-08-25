package pt.isel.ls.models.domain.movies;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class Movie {

	private int id;
	private String title;
	private int releaseYear;
	private String genre;
	private String cast;
	private String directors;
	private Date creationDate;
	private int[] ratings;
	private List<Review> reviews;

	public Movie(int id, String title, int releaseYear, String genre, String cast, String directors,
				 Date creationDate, int[] ratings, List<Review> reviews){
		this.id = id;
		this.title = title;
		this.releaseYear = releaseYear;
		this.genre = genre;
		this.cast = cast;
		this.directors = directors;
		this.creationDate = creationDate;
		this.ratings = ratings;
		this.reviews = reviews;
	}

	public Movie(String title, int releaseYear){
		this(-1, title, releaseYear, null, null, null, null, new int[]{0,0,0,0,0}, Collections.emptyList());
	}

	public int getId() { return id; }
	public String getTitle() { return title; }
	public int getReleaseYear() { return releaseYear; }
	public String getGenre() { return genre; }
	public String getCast() { return cast; }
	public String getDirectors() { return directors; }
	public Date getCreationDate() { return creationDate; }
	public int[] getRatings() { return ratings; }
	public List<Review> getReviews() { return reviews; }

	/**
	 * Get the average rating for this movie.
	 * @return Average rating.
	 */
	public int getAverageRating(){
		int count = getRatingsCount();
		int sum = 0;
		for(int i=0; i < ratings.length; ++i){
			sum += (i+1) * ratings[i];
		}
		return (sum == 0) ? 0 : (sum + count -1) / count;
	}

	/**
	 * Get the average rating for this movie.
	 * The string is represented with stars(*****).
	 * @return Average rating.
	 */
	public String getAverageRatingRepresentation(){
		int count = getRatingsCount();
		int sum = 0;
		for(int i=0; i < ratings.length; ++i){
			sum += (i+1) * ratings[i];
		}
		int avg = (sum == 0) ? 0 : (sum + count -1) / count;
		String out = "";
		for(int i=0; i < avg; ++i){
			out += "*";
		}
		return out;
	}

	/**
	 * Get total number of ratings for this movie:
	 * @return Rating count.
	 */
	private int getRatingsCount(){
		int count = 0;
		for (int value : ratings) count += value;
		return count;
	}

	@Override
	public String toString(){
		String ls = System.getProperty("line.separator");
		return
				" --- Movie ---" + ls +
				"title: " + title + ls +
				"releaseYear: " + ls +
				"genre: " + genre + ls +
				"cast: " + cast + ls +
				"directors: " + directors;
	}

	@Override
	public boolean equals(Object obj){
		if (obj == null || !(obj instanceof Movie)){
			return false;
		}
		Movie other = (Movie)obj;
		return title.equals(other.getTitle()) && releaseYear == other.releaseYear;
	}
}