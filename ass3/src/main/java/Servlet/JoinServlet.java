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

// Annotation to declare the servlet configuration, including its name and the URL pattern it serves
@WebServlet(name = "JoinServlet", value = "/JoinServlet")
public class JoinServlet extends HttpServlet {
    private CourseDAO courseDAO;

    // Servlet initialization method
    @Override
    public void init() {
        courseDAO = new CourseDAO();
    }

    // Method to handle POST requests for course enrollment
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
        response.sendRedirect("studentDashboard.jsp");
    }

    // Private method to perform the enrollment logic
    private boolean enrollStudentInCourse(int userId, int courseId) {
        // SQL statement to insert a new enrollment record into the User_Course table
        String sql = "INSERT INTO User_Course (user_id, course_id) VALUES (?, ?)";
        try (Connection conn = Utility.DatabaseConnection.getConnection();// Get a database connection
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {// Prepare the SQL statement

            // Set the parameters for the prepared statement with the user and course IDs
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);

            // Execute the update and check if it was successful
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;// Return false if the enrollment attempt failed
    }
}
