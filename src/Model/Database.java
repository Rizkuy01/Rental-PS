package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private String user = "root";
	private String password = "";
	private String url = "jdbc:mysql://localhost/psrentalsystem";
	private Statement statement;

	public Database() {
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Statement getStatement() {
		return statement;
	}

	public int getTotalPSUnits() {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM pss");
			if (resultSet.next()) {
				return resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getTotalUsers() {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM users");
			if (resultSet.next()) {
				return resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getTotalRents() {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM rents");
			if (resultSet.next()) {
				return resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getRecentActivity() {
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT action FROM activities ORDER BY timestamp DESC LIMIT 1");
			if (resultSet.next()) {
				return resultSet.getString("action");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "No recent activities.";
	}
}