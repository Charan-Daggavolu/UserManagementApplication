package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConfigs {
	private String jdbcURL = "jdbc:mysql://localhost:3306/user_data_jdbc?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Venya@0389";

	private static JdbcConfigs instance;

	private JdbcConfigs() {

	}

	public static synchronized JdbcConfigs getInstance() {
		if (instance == null) {
			instance = new JdbcConfigs();
		}
		return instance;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
