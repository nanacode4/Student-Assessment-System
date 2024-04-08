package Servlet;

import java.io.*;
import DAO.UserDAO;
import Model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// Annotation to declare servlet configuration
@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private String message;
    private UserDAO userDAO;

    // Servlet initialization method
    public void init() {
        message = "Hello World!";
        // Initialize UserDAO to interact with the database
        userDAO = new UserDAO();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve username and password from the request
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        // Try to validate the user with the provided credentials
        User user = userDAO.validateUser(userName, password);

        // Check if user validation was successful
        if (user != null && user.getUserId() > 0) { // UserIDs are typically positive integers
            // Create a new session for the logged-in user
            HttpSession session = request.getSession();
            // Store the userID in the session for later use
            session.setAttribute("userId", user.getUserId());

            // Redirect the user based on their role
            switch (user.getRole().toLowerCase()) {
                case "admin":// Redirect to adminDashboard
                    response.sendRedirect("adminDashboard.jsp");
                    break;
                case "student":// Redirect to studentDashboard
                    response.sendRedirect("studentDashboard.jsp");
                    break;
                case "teacher":// Redirect to teacherDashboard
                    response.sendRedirect("teacherDashboard.jsp");
                    break;
                default:
                    // Handle unknown roles by invalidating the session and redirecting to the login page with an error
                    session.invalidate();
                    request.setAttribute("loginError", "Invalid role");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } else {
            // User validation failed, redirect back to the login page with an error message
            request.setAttribute("loginError", "Invalid username or password");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }

}











