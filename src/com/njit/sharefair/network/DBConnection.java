package com.njit.sharefair.network;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	/**
	 * This method is to get DB connection. Call this method where ever you want DB
	 * connection.
	 * 
	 * @author srini
	 * @return Connection
	 */
	public static Connection getConnection() {
		try {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			// Change Username and Password if required but do not commit
			String userName = "sa";
			String password = "P@55w0rd";
			String url = "jdbc:sqlserver://localhost:1433" + ";databaseName=ShareFairDB";
			Connection connection = DriverManager.getConnection(url, userName, password);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
