<%@ page import="java.util.List" %>
<%@ page import="Model.User" %>
<%@ page import="Model.Course" %>
<%@ page import="DAO.CourseDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Student Dashboard</title>
</head>

<body>
<h2>Student Dashboard </h2>
<%
  // Instantiating a CourseDAO object to interact with the database
  CourseDAO courseDAO = new CourseDAO();
  // Retrieving the userId from the session
  int userId = (int) session.getAttribute("userId");
  // Getting the list of courses the student is enrolled in
  List<Course> enrolledCourses = courseDAO.getEnrolledCourses(userId);
  // Getting the list of all available courses
  List<Course> availableCourses = courseDAO.getAllCourses();

  // If the student is enrolled in any courses, display them
  if (enrolledCourses != null && !enrolledCourses.isEmpty()) {
%>
<h2>Your Enrolled Courses:</h2>
<ul>
  <% for(Course course : enrolledCourses) { %>
  <li>
    <!-- Each course name is a link to a page (mark.jsp) showing the course's details, including marks -->
    <a href="mark.jsp?courseId=<%= course.getCourseId() %>"><%= course.getCourseName() %></a>
  </li>
  <% } %>
</ul>


<%
  // If there are available courses that the student isn't enrolled in, display them
} else if (availableCourses != null && !availableCourses.isEmpty()) {
%>
<h2>Available Courses:</h2>
<ul>
  <% for(Course course : availableCourses) { %>
  <li>
    <%= course.getCourseName() %>
    <!-- Form for registering to a course. Upon submission, the form sends the courseId to JoinServlet -->
    <form action="JoinServlet" method="post">
      <input type="hidden" name="courseId" value="<%= course.getCourseId() %>" />
      <button type="submit">Register</button>
    </form>
  </li>
  <% } %>
</ul>
<% } %>




</body>
</html>


