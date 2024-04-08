package Servlet;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import DAO.CourseDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// Servlet annotation configuration to define its name and the URL pattern it serves
@WebServlet(name = "teacherServlet", value = "/teacherServlet")
public class teacherServlet extends HttpServlet{

    // Declare a CourseDAO instance for database operations
    private CourseDAO courseDAO;

    // Servlet initialization method
    @Override
    public void init() {
        courseDAO = new CourseDAO();// Initialize the CourseDAO object
    }

    // Handles the POST request sent to this servlet
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the courseID to enroll in
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        // Get the userID from session
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        // Try to enroll the student in the course
        boolean isEnrolled = enrollStudentInCourse(userId, courseId);

        if (isEnrolled) {
            // Enrollment successful, redirect with success message
            session.setAttribute("successMessage", "Enrolled in course successfully!");
        } else {
            // Enrollment failed, redirect with error message
            session.setAttribute("errorMessage", "Failed to enroll in course. Please try again.");
        }

        // Redirect back to the student dashboard or appropriate page
        response.sendRedirect("teacherDashboard.jsp");
    }

    // A  method to perform the database operation of enrolling a student in a course
    private boolean enrollStudentInCourse(int userId, int courseId) {

        // SQL statement to insert a new record into the User_Course table, which maps users to courses
        String sql = "INSERT INTO User_Course (user_id, course_id) VALUES (?, ?)";

        try (Connection conn = Utility.DatabaseConnection.getConnection();// Obtain a database connection
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {// Prepare the SQL statement

            // Set the userID and courseID in the prepared statement
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);

            // Execute the update and check if it was successful
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;// Return false if the enrollment operation fails
    }

}
