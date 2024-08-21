package com.usermanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.usermanagement.model.User;

import services.JdbcConfigs;

public class UserDao {
	private JdbcConfigs jdbcConfigs = JdbcConfigs.getInstance();

	private static final String INSERT_USERS_SQL = "INSERT INTO users (first_name, last_name, username, email, address, contact, country_code) VALUES (?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_ALL_USERS_SQL = "SELECT id, first_name, last_name, username, email, address, contact, country_code FROM users";
	private static final String SEARCH_USERS_SQL = "SELECT id, first_name, last_name, username, email, address, contact, country_code FROM users WHERE username LIKE ? OR first_name LIKE ? OR last_name LIKE ?";
	private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?";
	private static final String EDIT_USER_SQL = "SELECT id, first_name, last_name, username, email, address, contact, country_code FROM users WHERE id =?";
	private static final String UPDATE_USERS_SQL = "UPDATE users set first_name = ?, last_name = ?, username = ?, email = ?, address = ?, contact = ?, country_code = ? where id = ?;";

	public void insertUser(User user) throws ClassNotFoundException {
		try (Connection connection = jdbcConfigs.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getAddress());
			preparedStatement.setString(6, user.getContact());
			preparedStatement.setString(7, user.getCountryCode());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateUser(User user, String editId) throws ClassNotFoundException {
		try (Connection connection = jdbcConfigs.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getAddress());
			preparedStatement.setString(6, user.getContact());
			preparedStatement.setString(7, user.getCountryCode());
			preparedStatement.setString(8, editId);
			int rowsUpdated = preparedStatement.executeUpdate();
			System.out.println("Rows updated: " + rowsUpdated);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> selectAllUsers() {
		List<User> users = new ArrayList<>();
		try (Connection connection = jdbcConfigs.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_SQL)) {
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String contact = rs.getString("contact");
				String countryCode = rs.getString("country_code");
				User user = new User(firstName, lastName, username, email, address, contact, countryCode);
				user.setId(id);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public User editUser(String userId) {
		int id = Integer.parseInt(userId);
		User user = null;
		try (Connection connection = jdbcConfigs.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(EDIT_USER_SQL);) {
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String contact = rs.getString("contact");
				String countryCode = rs.getString("country_code");
				user = new User(firstName, lastName, username, email, address, contact, countryCode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<User> searchUsers(String keyword) {
		List<User> users = new ArrayList<>();
		try (Connection connection = jdbcConfigs.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USERS_SQL)) {
			String searchKeyword = "%" + keyword + "%";
			preparedStatement.setString(1, searchKeyword);
			preparedStatement.setString(2, searchKeyword);
			preparedStatement.setString(3, searchKeyword);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String contact = rs.getString("contact");
				String countryCode = rs.getString("country_code");
				User user = new User(firstName, lastName, username, email, address, contact, countryCode);
				user.setId(id);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public void deleteUser(String userId) {
		int id = Integer.parseInt(userId);
		try (Connection connection = jdbcConfigs.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
