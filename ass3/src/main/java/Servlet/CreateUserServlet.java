package Servlet;

import DAO.UserDAO;
import Model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

// Annotation to declare servlet configuration, specifying its name and URL pattern
@WebServlet(name = "CreateUserServlet", urlPatterns = {"/CreateUserServlet"})
public class CreateUserServlet extends HttpServlet {
    // Declare a variable for the UserDAO to interact with the database
    private UserDAO userDAO;

    // Servlet initialization method
    public void init() {
        userDAO = new UserDAO();// Initialize the UserDAO
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Retrieve user details from the request parameters
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        // Create a new User object with the retrieved information
        User newUser = new User( userName, password, firstName, lastName, phone, role);

        // Attempt to create the user in the database using userDAO
        boolean isUserCreated = userDAO.createUser(newUser);

        if (isUserCreated) {
            // If user creation was successful, set a success message in session scope and redirect to the admin dashboard
            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "User created successfully!");
            response.sendRedirect("adminDashboard.jsp");
        } else {
            // If user creation failed, redirect to the admin dashboard with an indicator of failure
            response.sendRedirect("adminDashboard.jsp?success=false");
        }

    }


}
