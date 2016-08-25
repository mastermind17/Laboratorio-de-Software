package pt.isel.ls.models.mappers;

import pt.isel.ls.app.App;
import pt.isel.ls.models.exceptions.RatingException;
import pt.isel.ls.services.sql.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingMapper {

	private static final String INSERT_ONE_STAR_RATING =
			"update Movie set oneStar = oneStar+1 where id = (?)";

	private static final String INSERT_TWO_STARS_RATING =
			"update Movie set twoStars = twoStars+1 where id = (?)";

	private static final String INSERT_THREE_STARS_RATING =
			"update Movie set threeStars = threeStars+1 where id = (?)";

	private static final String INSERT_FOUR_STARS_RATING =
			"update Movie set fourStars = fourStars+1 where id = (?)";

	private static final String INSERT_FIVE_STARS_RATING =
			"update Movie set fiveStars = fiveStars+1 where id = (?)";

	public static void insert(int rating, int mid) throws SQLException, RatingException {
		Connection con = App.getConnectionWrapper().getConnection();
		String sql;
		switch (rating){
			case 1: sql = INSERT_ONE_STAR_RATING; break;
			case 2: sql = INSERT_TWO_STARS_RATING; break;
			case 3: sql = INSERT_THREE_STARS_RATING; break;
			case 4: sql = INSERT_FOUR_STARS_RATING; break;
			case 5: sql = INSERT_FIVE_STARS_RATING; break;
			default: throw new RatingException(rating);
		}
 		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, mid);
		stmt.executeUpdate();
		JDBCUtils.closeJDBCObjects(new Object[]{con,stmt});
	}
}
