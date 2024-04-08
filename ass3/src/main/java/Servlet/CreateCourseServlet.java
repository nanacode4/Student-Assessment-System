package Servlet;

import DAO.CourseDAO;
import Model.Course;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

// Servlet configuration annotation specifying its name and the URL pattern it responds to
@WebServlet(name = "CreateCourseServlet", urlPatterns = {"/CreateCourseServlet"})
public class CreateCourseServlet extends HttpServlet {
    private CourseDAO courseDAO;// Declaration of CourseDAO to interact with the database

    // Initialization method for the servlet
    public void init() {
        courseDAO = new CourseDAO();// Initialize CourseDAO to perform database operations
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Extract course details from the request parameters
            String courseName = request.getParameter("courseName");
            String semester = request.getParameter("semester");

            // Create a new Course object with the retrieved information
            Course newCourse = new Course(courseName, semester);

            // Attempt to create the course in the database
            boolean isCourseCreated = courseDAO.createCourse(newCourse);

            if (isCourseCreated) {
                // successful, set a success message in the session scope and redirect to the admin dashboard
                HttpSession session = request.getSession();
                session.setAttribute("successMessage", "Course created successfully!");
                response.sendRedirect("adminDashboard.jsp");
            } else {
                // fails, redirect to the admin dashboard with an indication of failure
                response.sendRedirect("adminDashboard.jsp?success=false");
            }

        } catch (NumberFormatException e) {
            // Catch and handle the exception if courseId parsing fails, which could occur if courseId were used and not properly formatted
            response.sendRedirect("adminDashboard.jsp?success=false&error=invalid_course_id");
        }
    }
}
