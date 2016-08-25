package pt.isel.ls.models.mappers;

import pt.isel.ls.app.App;
import pt.isel.ls.models.domain.movies.Movie;
import pt.isel.ls.models.domain.movies.MovieCollection;
import pt.isel.ls.services.sql.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectionMapper {

	private static final String INSERT_COLLECTION = "insert into MovieCollection(name,description) values((?), (?))";

	private static final String INSERT_MOVIE_TO_COLLECTION = "insert into Movie_MovieCollection(movieId,collectionId) values((?), (?))";

	private static final String SELECT_COLLECTION_WITH_MOVIES =
			"select * " +
			"from Movie_MovieCollection " +
					"inner join Movie on (movieId = Movie.id)" +
					"inner join MovieCollection on (Movie_MovieCollection.collectionId = MovieCollection.id)" +
			"where collectionId = (?)";

	private static final String SELECT_COLLECTION =
			"select * " +
			"from MovieCollection " +
			"where id = (?)";

	private static final String SELECT_MOVIE_COLLECTIONS =
			"select MovieCollection.id,name,description " +
			"from MovieCollection " +
					"inner join Movie_MovieCollection on (MovieCollection.id = collectionId) " +
			"where movieId = (?)";

	private static final String SELECT_COLLECTIONS =
			"select * " +
			"from MovieCollection";

	private static final String DELETE_MOVIE_FROM_COLLECTION =
			"delete from Movie_MovieCollection " +
			"where movieId = (?) and collectionId = (?)";


	public static int insertCollection(MovieCollection col) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(INSERT_COLLECTION, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, col.getName());
		stmt.setString(2, col.getDescription());
		stmt.executeUpdate();
		//get movie id
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		int out = rs.getInt(1);
		JDBCUtils.closeJDBCObjects(new Object[]{con, stmt, rs});
		return out;
	}

	public static int insertMovieInCollection(int mid, int cid) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(INSERT_MOVIE_TO_COLLECTION, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, mid);
		stmt.setInt(2, cid);
		stmt.executeUpdate();
		//get id
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		int out = rs.getInt(1);
		JDBCUtils.closeJDBCObjects(new Object[]{con, stmt, rs});
		return out;
	}

	public static void deleteMovieFromCollection(int mid, int cid) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(DELETE_MOVIE_FROM_COLLECTION);
		stmt.setInt(2, cid);
		stmt.setInt(1, mid);
		stmt.executeUpdate();
		JDBCUtils.closeJDBCObjects(new Object[]{con, stmt});
	}

	public static List<MovieCollection> getAllOfMovie(int mid) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(SELECT_MOVIE_COLLECTIONS);
		stmt.setInt(1, mid);
		ResultSet rs = stmt.executeQuery();
		ArrayList<MovieCollection> movieCollections = new ArrayList<>();
		while (rs.next()) {
			movieCollections.add(new MovieCollection(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("description"),
					null
			));
		}
		JDBCUtils.closeJDBCObjects(new Object[]{con, stmt, rs});
		return movieCollections;
	}

	public static List<MovieCollection> getAll() throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(SELECT_COLLECTIONS);
		ResultSet rs = stmt.executeQuery();
		ArrayList<MovieCollection> movieCollections = new ArrayList<>();
		while (rs.next()) {
			movieCollections.add(new MovieCollection(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("description"),
					null
			));
		}
		JDBCUtils.closeJDBCObjects(new Object[]{con, stmt, rs});
		return movieCollections;
	}

	public static MovieCollection get(int id) throws SQLException {
		Connection con = App.getConnectionWrapper().getConnection();
		PreparedStatement stmt = con.prepareStatement(SELECT_COLLECTION);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		//in case the movie doesn't exist the exception is launch
		rs.next();
		String[] aux = { rs.getString("name"), rs.getString("description") };
		int aux2 = rs.getInt("id");
		stmt = con.prepareStatement(SELECT_COLLECTION_WITH_MOVIES);
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		ArrayList<Movie> ms = new ArrayList<>();
		while(rs.next()){
			int[] ratings = new int[]{ rs.getInt("oneStar"), rs.getInt("twoStars"), rs.getInt("threeStars"),
					rs.getInt("fourStars"), rs.getInt("fiveStars")};
			ms.add(new Movie(
					rs.getInt("id"),
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
		MovieCollection out = new MovieCollection(aux2, aux[0], aux[1], toArray(ms));
		JDBCUtils.closeJDBCObjects(new Object[]{con,stmt,rs});
		return out;
	}

	private static Movie[] toArray(List<Movie> ms){
		Movie[] out = new Movie[ms.size()];
		for(int i=0; i < ms.size(); ++i){
			out[i] = ms.get(i);
		}
		return out;
	}
}
