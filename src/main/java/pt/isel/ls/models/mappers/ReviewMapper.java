package pt.isel.ls.models.mappers;

import pt.isel.ls.app.App;
import pt.isel.ls.models.domain.movies.Review;
import pt.isel.ls.services.sql.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewMapper {

	private static final String INSERT_REVIEW =
			"insert into Review(critic,summary,content,rating,movieId) values(?,?,?,?,?)";

	private static final String SELECT_MOVIE_REVIEWS =
			"select id,critic,summary,content,rating,movieId " +
			"from Review " +
			"where movieId = (?)";

	private static final String SELECT_MOVIE_REVIEW =
			"select id,critic,summary,content,rating,movieId " +
			"from Review " +
			"where id = (?) and movieId = (?)";


	/**
	 * Insert a record on the ContentReview table for the ContentMovie identified by mid
	 *
	 * @param rev ContentReview
	 * @param mid ContentMovie id
	 * @return Inserted ContentReview id
	 * @throws SQLException
	 */
	public static int insert(Review rev, int mid) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		//insert review
		PreparedStatement stmt = con.prepareStatement(INSERT_REVIEW, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, rev.getCritic());
		stmt.setString(2, rev.getSummary());
		stmt.setString(3, rev.getContent());
		stmt.setInt(4, rev.getRating());
		stmt.setInt(5, mid);
		stmt.executeUpdate();
		//get review id
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		int out = rs.getInt(1);
		JDBCUtils.closeJDBCObjects(new Object[]{con,stmt,rs});
		return out;
	}

	public static Review getOfMovie(int mid, int id) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(SELECT_MOVIE_REVIEW);
		stmt.setInt(1, id);
		stmt.setInt(2, mid);
		ResultSet rs = stmt.executeQuery();
		//in case the movie doesn't exist the exception is launch
		rs.next();
		Review out = new Review(
				rs.getInt("id"),
				rs.getString("critic"),
				rs.getString("summary"),
				rs.getString("content"),
				rs.getInt("rating"));
		JDBCUtils.closeJDBCObjects(new Object[]{con,stmt,rs});
		return out;
	}

	public static List<Review> getAllOfMovie(int mid) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		ArrayList<Review> reviews = new ArrayList<>();
		PreparedStatement stmt = con.prepareStatement(SELECT_MOVIE_REVIEWS);
		stmt.setInt(1, mid);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			reviews.add(new Review(
					rs.getInt("id"),
					rs.getString("critic"),
					rs.getString("summary"),
					rs.getString("content"),
					rs.getInt("rating")));
		}
		JDBCUtils.closeJDBCObjects(new Object[]{con,stmt,rs});
		return reviews;
	}

}
