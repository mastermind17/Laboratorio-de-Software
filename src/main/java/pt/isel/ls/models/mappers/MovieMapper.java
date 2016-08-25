package pt.isel.ls.models.mappers;

import pt.isel.ls.app.App;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.services.sql.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieMapper {

	private static final String INSERT_MOVIE =
			"insert into Movie(title,releaseYear) values(?,?)";

	private static final String SELECT_MOVIE =
			"select id,title,releaseYear,genre,cast,directors,creationDate,oneStar,twoStars,threeStars,fourStars,fiveStars " +
			"from Movie " +
			"where id = (?)";

	private static final String SELECT_MOVIE_ID =
			"select id " +
			"from Movie " +
			"where title = (?) and releaseYear = (?)";

	private static final String SELECT_ALL_MOVIES =
			"select id,title,releaseYear,genre,cast,directors,creationDate,oneStar,twoStars,threeStars,fourStars,fiveStars " +
			"from Movie";

	public static int insert(Movie mov) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(INSERT_MOVIE, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, mov.getTitle());
		stmt.setInt(2, mov.getReleaseYear());
		stmt.executeUpdate();
		//get movie id
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		int out = rs.getInt(1);
		JDBCUtils.closeJDBCObjects(new Object[]{con,stmt,rs});
		return out;
	}

	public static Movie get(int id) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(SELECT_MOVIE);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		//in case the movie doesn't exist the exception is launch
		rs.next();
		int[] ratings = new int[]{ rs.getInt("oneStar"), rs.getInt("twoStars"), rs.getInt("threeStars"),
				rs.getInt("fourStars"), rs.getInt("fiveStars")};
		Movie out = new Movie(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getInt("releaseYear"),
				rs.getString("genre"),
				rs.getString("cast"),
				rs.getString("directors"),
				rs.getDate("creationDate"),
				ratings,
				ReviewMapper.getAllOfMovie(id)
		);
		JDBCUtils.closeJDBCObjects(new Object[]{con,stmt,rs});
		return out;
	}

	public static int getId(String title, int releaseYear) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(SELECT_MOVIE_ID);
		stmt.setString(1, title);
		stmt.setInt(2, releaseYear);
		ResultSet rs = stmt.executeQuery();
		//in case the movie doesn't exist the exception is launch
		rs.next();
		int out = rs.getInt("id");
		JDBCUtils.closeJDBCObjects(new Object[]{con,stmt,rs});
		return out;
	}

	public static List<Movie> getAll() throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(SELECT_ALL_MOVIES);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Movie> movies = new ArrayList<>();
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
}
