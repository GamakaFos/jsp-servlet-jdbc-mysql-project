package dao;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import model.User;
//import utils.JDBCUtils;
//
//public class UserDAO {
//	
//	//define sql statements
//	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES (?, ?, ?);";
//	private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
//	private static final String SELECT_ALL_USERS = "select * from users";
//	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
//	private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
//	
//	
//
//	
//	
//	
//	//insert user into db
//	public void insertUser(User user) throws ClassNotFoundException {
//		try(Connection connection = JDBCUtils.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
//			preparedStatement.setString(1, user.getName());
//			preparedStatement.setString(2, user.getEmail());
//			preparedStatement.setString(3, user.getCountry());
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//		} catch (Exception e) {
//			e.printStackTrace();;
//		} 
//	}
//	
//	
//	//select user from db
//	public User selectUser(int id) {
//		User user = null;
//		try(Connection connection = JDBCUtils.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)){
//			
//			preparedStatement.setInt(1, id);
//			ResultSet resultSet = preparedStatement.executeQuery();
//			while(resultSet.next()) {
//				String name = resultSet.getString("name");
//				String email = resultSet.getString("email");
//				String country = resultSet.getString("country");
//				
//				user = new User(name, email, country);
//			}
//			
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return user;
//	}
//	
//	
//	// select all users from db
//	public List<User> selectAllUsers(){
//		List<User> users = new ArrayList<>();
//		
//		try(Connection connection = JDBCUtils.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)){
//			
//			ResultSet resultSet = preparedStatement.executeQuery();
//			
//			while(resultSet.next()) {
//				int id = resultSet.getInt("id");
//				String name = resultSet.getString("name");
//				String email = resultSet.getString("email");
//				String country = resultSet.getString("country");
//				
//				users.add(new User(id, name, email, country));
//			}
//			
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return users;
//	}
//	
//	
//	//delete user from db
//	public boolean deleteUser(int id) {
//		boolean rowDeleted = false;
//		try(Connection connection = JDBCUtils.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)){
//			
//			preparedStatement.setInt(1, id);
//			
//			//if user is deleted execute update returns value of 1
//			rowDeleted = preparedStatement.executeUpdate() > 0;
//			
//			
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return rowDeleted;
//	}
//	
//	
//	//update user in db
//	public boolean updateUser(User user) {
//		boolean rowUpdated = false;
//		try(Connection connection = JDBCUtils.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)){
//			
//			//didnt realy get, how it will update our uesr
//			preparedStatement.setString(1, user.getName());
//			preparedStatement.setString(2, user.getEmail());
//			preparedStatement.setString(3, user.getCountry());
//			preparedStatement.setInt(4, user.getId());
//			
//			rowUpdated = preparedStatement.executeUpdate() > 0;
//			
//		} catch (SQLException e) {
//			JDBCUtils.printSQLException(e);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return rowUpdated;
//	}
//	
//	
//}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "user";

    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES " +
        " (?, ?, ?);";

    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";

    public UserDAO() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public void insertUser(User user) throws SQLException {
        System.out.println(INSERT_USERS_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public User selectUser(int id) {
        User user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List < User > selectAllUsers() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < User > users = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
