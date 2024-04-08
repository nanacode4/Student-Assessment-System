package Utility;

import java.sql.*;

public class DatabaseConnection {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/student_assessment_system";
    private static final String USER = "root";
    private static final String PASS = "cscscs61B1234";
    private static Connection connection = null;

    // A static method that returns a Connection object to the database
    public static Connection getConnection() {

        try {
            // Explicitly load the MySQL JDBC driver to ensure it's available at runtime
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Attempt to establish a connection to the database using the provided URL, user, and password
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            // Catch any SQLException or ClassNotFoundException and print the stack trace
            e.printStackTrace();
        }

        // Return the Connection object, which may be null if the connection attempt failed
        return connection;
    }



}


