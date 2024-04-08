package DAO;
// Import statements to use various classes and interfaces
import Model.*;
import Utility.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    // Method to insert a new course record into the database
    public boolean createCourse(Course course){
        // SQL query to insert a new course into the database
        String sql = "INSERT INTO Course ( course_name, semester) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();// Get a connection to the database
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {// Prepare the SQL statement

            // Set parameters for the prepared statement from the course object
            preparedStatement.setString(1,course.getCourseName());
            preparedStatement.setString(2,course.getSemester());

            // Execute the update
            int  affectedRows = preparedStatement.executeUpdate();
            // Check if the insert was successful
            return affectedRows > 0;
        }catch (SQLException e){
            System.out.println("Error creating course: " + e.getMessage());
            e.printStackTrace();
            return false;// Return false if there was an error
        }
    }
    // Method to get courses a specific student is enrolled in
    public List<Course> getEnrolledCourses(int studentId) {
        List<Course> courses = new ArrayList<>();
        // SQL query to select courses joined to a user through the User_Course table
        String sql = "SELECT c.course_id, c.course_name, c.semester FROM Course c " +
                "JOIN User_Course uc ON c.course_id = uc.course_id " +
                "WHERE uc.user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();// Get a connection to the database
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {// Prepare the SQL statement

            // Set the student ID in the SQL query
            preparedStatement.setInt(1, studentId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    // Create a new Course object and set its properties from the result set
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setSemester(rs.getString("semester"));
                    // Add the course to the list of courses
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;// Return the list of courses
    }

    // Method to get all available courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course";// SQL query to select all courses

        try (Connection conn = DatabaseConnection.getConnection();// Get a connection to the database
             PreparedStatement preparedStatement = conn.prepareStatement(sql);// Prepare the SQL statement
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                // Create a new Course object and set its properties from the result set
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setSemester(rs.getString("semester"));
                // Add the course to the list of courses
                courses.add(course);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;// Return the list of courses
    }

    // Retrieves a list of User objects representing students enrolled in a specific course
    public List<User> getStudentsForCourse(int courseId) {
        List<User> students = new ArrayList<>();
        // SQL query to select users who are students in a specific course
        String sql = "SELECT * FROM User WHERE user_id IN (SELECT user_id FROM User_Course WHERE course_id = ?) AND role = 'student'";

        try (Connection conn = DatabaseConnection.getConnection();// Get a connection to the database
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {// Prepare the SQL statement

            preparedStatement.setInt(1, courseId);// Set the course ID in the SQL query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Create a new User object for each student and set its properties
                User student = new User();
                student.setUserId(resultSet.getInt("user_id"));
                student.setUserName(resultSet.getString("username"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setPhone(resultSet.getString("phone"));
                student.setRole(resultSet.getString("role"));
                students.add(student);// Add the student to the list of students
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;// Return the list of students
    }


    // Retrieves a Course object by its ID
    public Course getCourseById(int courseId) {
        // SQL query to find a course by its ID
        String sql = "SELECT * FROM Course WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();// Get a connection to the database
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {// Prepare the SQL statement

            // Set the course ID in the SQL query
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Create a new Course object and set its properties from the result set
                Course course = new Course();
                course.setCourseId(resultSet.getInt("course_id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setSemester(resultSet.getString("semester"));
                return course;// Return the course
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;// Return null if the course was not found
    }


}




