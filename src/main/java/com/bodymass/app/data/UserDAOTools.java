package com.bodymass.app.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOTools {
	private String url = "jdbc:mysql://localhost/mydb?" + "useUnicode=true&serverTimezone=UTC" + "&user=root&password=12345";

	public Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(url);
		return conn;
	}
	
	public List<User> selectAllUsers() throws SQLException {
		List<User> result = new ArrayList<>();

		Connection conn = getConnection();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT id, email, password FROM users");
		while (rs.next()) {
			int id = rs.getInt("id");
			String email = rs.getString("email");
			String password = rs.getString("password");

			result.add(new User(id, email, password));
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}
		return result;
	}
	
	public void addUser(User user) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement preparedStatement = conn
				.prepareStatement("INSERT INTO users(id, email, password) VALUES(?, ?, ?)");
		preparedStatement.setLong(1, user.getId());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getPassword());
		int row = preparedStatement.executeUpdate();

		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}
	}
	
	public void deleteUsers(User user) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM users WHERE id=?");
		preparedStatement.setLong(1, user.getId());		
		int row = preparedStatement.executeUpdate();

		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}
	}
	
	public void updateUsers(User user) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET id=?, email=? where password=?");
		preparedStatement.setLong(1, user.getId());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getPassword());		
		int row = preparedStatement.executeUpdate();

		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}
	}

	public User findUser(String email, String password) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, email, password FROM users WHERE email=? and password=?");
		preparedStatement.setString(1, email);
		preparedStatement.setString(2, password);

		ResultSet rs = preparedStatement.executeQuery("SELECT email, password FROM users WHERE id=?");
		if (rs.next()) {
			int id = rs.getInt("id");
			String newEmail = rs.getString("email");
			String newPassword = rs.getString("password");

			return new User(id, newEmail, newPassword);
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}

		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlEx) {
			} // ignore
		}
		return null;
	}
}