package pt.isel.ls.services.sql;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtils {

	/**
	 * Closes the given JDBC objects.
	 * @param array Array of objects.
	 */
	public static void closeJDBCObjects(Object[] array){
		for(Object obj : array){
			try {
				((AutoCloseable)obj).close();
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Unexpected sql error.");
			}
		}
	}

	/**
	 * Counts the number of rows.
	 * The PreparedStatement associated with the given ResultSet must have been created with the
	 * following configuration:
	 * resultSetType = ResultSet.TYPE_SCROLL_INSENSITIV
	 * resultSetConcurrency = ResultSet.CONCUR_READ_ONLY
	 * @param rs ResultSet.
	 * @return Number of Records.
	 */
	public static int getNumberOfRecords(ResultSet rs) throws SQLException {
		boolean bool = rs.last();
		int numberOfRecords = 0;
		if(bool){
			numberOfRecords = rs.getRow();
		}
		//move the cursor back to the beginning
		rs.beforeFirst();
		return numberOfRecords;
	}

	/**
	 * Setting "java.library.path" programmatically, so that it works with gradle
	 * Explanation: http://blog.cedarsoft.com/2010/11/setting-java-library-path-programmatically/
	 */
	private static void setUpJDBCLibrary() throws IllegalAccessException, NoSuchFieldException {
		System.setProperty("java.library.path", System.getProperty("user.dir") + "\\vendor\\main");
		Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths");
		fieldSysPath.setAccessible(true);
		fieldSysPath.set(null, null);
	}
}
