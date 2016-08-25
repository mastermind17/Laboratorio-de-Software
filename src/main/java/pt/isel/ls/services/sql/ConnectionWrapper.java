package pt.isel.ls.services.sql;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionWrapper {

	private SQLServerDataSource dataSource;
	// movies connection info
	private final String databaseName = "LS_PROJECT";
	private final String serverName = "localhost";
	private final boolean integratedSecurity = true;
	private final int portNumber = 1433;

	public ConnectionWrapper() {
		dataSource = new SQLServerDataSource();
		configureDataSource();
	}
	
	/**
	 * Creates a SQLServerDataSource to establishes a connection with the movies.
	 */
	private void configureDataSource() {
		// remove database
//		dataSource.setServerName(getFromEnv("LsSqlServer"));
//		dataSource.setUser(getFromEnv("LsSqlUser"));
//		dataSource.setPassword(getFromEnv("LsSqlPassword"));
		// local database
		dataSource.setServerName(serverName);
		dataSource.setDatabaseName(databaseName);
		dataSource.setPortNumber(portNumber);
		dataSource.setIntegratedSecurity(integratedSecurity);
	}

	private String getFromEnv(String name)  {
		String value = System.getenv(name);
		if(value == null){
			throw new RuntimeException ("No value found for environment variable " + name);
		}
		return value;
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
