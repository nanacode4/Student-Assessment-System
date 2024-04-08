package DAO;

// Importing necessary classes for database connection and manipulation.
import Model.User;
import Utility.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    // Method to create a new user in the database.
    public boolean createUser(User user) {
        // SQL query to insert a new user into the database
        String sql = "INSERT INTO User (username, password, first_name, last_name, phone, role) VALUES (?, ?, ?, ?, ?, ?)";


        try(Connection conn = DatabaseConnection.getConnection();// Establishing database connection.
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {// Preparing the SQL query.

            // Setting the user details in the SQL query placeholders.
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setString(6, user.getRole());

            // Execute the update
            int affectedRows = preparedStatement.executeUpdate();
            // Check if the insert was successful
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            return false;// Return false if user creation failed.
        }
    }

    // Method to validate a user's login credentials.
    public User validateUser(String userName, String password) {

        // SQL query to find a user by username and password.
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();// Establishing database connection.
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {// Preparing the SQL query.

            // Setting the login credentials in the SQL query.
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            // Executing the query.
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a user is found, initialize a new User object with the fetched details.
            if (resultSet.next()) {

                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getString("role"));
                return user;// Return the found User object.

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found or error
    }

    // Method to fetch a user by their ID from the database.
    public User getUserById(int userId) {
        // SQL query to find a user by their user ID.
        String sql = "SELECT * FROM User WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();// Establishing database connection.
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {// Preparing the SQL query.

            // Setting the user ID in the SQL query.
            preparedStatement.setInt(1, userId);
            // Executing the query.
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a user is found, initialize a new User object with the fetched details.
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getString("role"));
                return user;// Return the found User object.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;// Return null if no user is found or if an error occurs.
    }

}



   


