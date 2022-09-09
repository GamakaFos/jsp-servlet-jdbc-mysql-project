package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDAO {
	private String jdbcUrl= "jdbc:mysql://localhost:3306/java_demo?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "user";
	
	//define sql statements
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES (?, ?, ?);";
	private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
	
	
	// funcion of getting connection
	protected Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	
	
	//insert user into db
	public void insertUser(User user) {
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.executeUpdate();
			
		} catch (Exception e){
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	
	//select user from db
	public User selectUser(int id) {
		User user = null;
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)){
			
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String country = resultSet.getString("country");
				
				user = new User(name, email, country);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	// select all users from db
	public List<User> selectAllUsers(){
		List<User> users = new ArrayList<>();
		
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)){
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String country = resultSet.getString("country");
				
				users.add(new User(id, name, email, country));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	
	//delete user from db
	public boolean deleteUser(int id) {
		boolean rowDeleted = false;
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)){
			
			preparedStatement.setInt(1, id);
			
			//if user is deleted execute update returns value of 1
			rowDeleted = preparedStatement.executeUpdate() > 0;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rowDeleted;
	}
	
	
	//update user in db
	public boolean updateUser(User user) {
		boolean rowUpdated = false;
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)){
			
			//didnt realy get, how it will update our uesr
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setInt(4, user.getId());
			
			rowUpdated = preparedStatement.executeUpdate() > 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rowUpdated;
	}
	
	
}
