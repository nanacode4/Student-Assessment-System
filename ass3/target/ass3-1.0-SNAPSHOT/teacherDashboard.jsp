<%@ page import="java.util.List" %>
<%@ page import="Model.Course" %>
<%@ page import="DAO.CourseDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher Dashboard</title>
</head>
<body>
<h2>Teacher Dashboard</h2>
<%
    // Instantiate CourseDAO to interact with the course data in the database
    CourseDAO courseDAO = new CourseDAO();
    // Retrieve the userId from the session, representing the current user
    int userId = (int) session.getAttribute("userId");
    // Get the list of courses the teacher is currently enrolled in
    List<Course> enrolledCourses = courseDAO.getEnrolledCourses(userId);
    // Get the list of all available courses
    List<Course> availableCourses = courseDAO.getAllCourses();

    // If the teacher is enrolled in any courses, display them
    if (enrolledCourses != null && !enrolledCourses.isEmpty()) {
%>
<h2>Teacher Courses:</h2>
<ul>
    <% for(Course course : enrolledCourses) { %>
    <li><%= course.getCourseName() %>
        <!-- Provide a link to view students enrolled in each course -->
        <a href="courseStudents.jsp?courseId=<%= course.getCourseId() %>">View Students</a>
    </li>
    <% } %>
</ul>


<%
    // If there are available courses, list them with the option to register
} else if (availableCourses != null && !availableCourses.isEmpty()) {
%>
<h2>Available Courses:</h2>
<ul>
    <% for(Course course : availableCourses) { %>
    <li>
        <!-- Each course has a form to register for it, submitting to the teacherServlet -->
        <%= course.getCourseName() %>
        <form action="teacherServlet" method="post">
            <input type="hidden" name="courseId" value="<%= course.getCourseId() %>" />
            <button type="submit">Register</button>
        </form>
    </li>
    <% } %>
</ul>
<% } %>



</body>
</html>
