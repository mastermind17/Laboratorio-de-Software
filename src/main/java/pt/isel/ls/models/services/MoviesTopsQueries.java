package pt.isel.ls.models.services;

import pt.isel.ls.app.App;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.mappers.ReviewMapper;
import pt.isel.ls.services.sql.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoviesTopsQueries {

	private static final String SELECT_MOVIES_WITH_MORE_REVIEWS =
			"select top (?) count(Review.id) as count,movieId,Movie.id,title,releaseYear,genre,cast,directors,creationDate,oneStar,twoStars,threeStars,fourStars,fiveStars " +
					"from Review inner join Movie on (Movie.id = Review.movieId) " +
					"group by movieId,Movie.id,title,releaseYear,genre,cast,directors,creationDate,oneStar,twoStars,threeStars,fourStars,fiveStars " +
					"order by count desc";

	private static final String SELECT_MOVIES_WITH_LESS_REVIEWS =
			"select top (?) count(Review.id) as count,movieId,Movie.id,title,releaseYear,genre,cast,directors,creationDate,oneStar,twoStars,threeStars,fourStars,fiveStars " +
					"from Review inner join Movie on (Movie.id = Review.movieId) " +
					"group by movieId,Movie.id,title,releaseYear,genre,cast,directors,creationDate,oneStar,twoStars,threeStars,fourStars,fiveStars " +
					"order by count asc";

	public static List<Movie> getNMoviesWithMoreReviews(int n) throws SQLException {
		return getMovies(n, SELECT_MOVIES_WITH_MORE_REVIEWS);
	}

	public static List<Movie> getNMoviesWithLessReviews(int n) throws SQLException {
		return getMovies(n, SELECT_MOVIES_WITH_LESS_REVIEWS);
	}

	private static List<Movie> getMovies(int n, String statement) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(statement);
		stmt.setInt(1, n);
		ResultSet rs = stmt.executeQuery();
		List<Movie> movies = new ArrayList<>();
		while(rs.next()){
			int[] ratings = new int[]{ rs.getInt("oneStar"), rs.getInt("twoStars"), rs.getInt("threeStars"),
					rs.getInt("fourStars"), rs.getInt("fiveStars")};
			int id = rs.getInt("id");
			movies.add(new Movie(
					id,
					rs.getString("title"),
					rs.getInt("releaseYear"),
					rs.getString("genre"),
					rs.getString("cast"),
					rs.getString("directors"),
					rs.getDate("creationDate"),
					ratings,
					ReviewMapper.getAllOfMovie(id)
			));
		}
		JDBCUtils.closeJDBCObjects(new Object[]{con,stmt,rs});
		return movies;
	}

	public static Movie getMovieWithMoreReviews() throws SQLException {
		List<Movie> movies = MoviesTopsQueries.getNMoviesWithMoreReviews(1);
		return movies.isEmpty() ? null : movies.get(0);
	}

	public static Movie getMovieWithLessReviews() throws SQLException {
		List<Movie> movies = MoviesTopsQueries.getNMoviesWithLessReviews(1);
		return movies.isEmpty() ? null : movies.get(0);
	}
}
