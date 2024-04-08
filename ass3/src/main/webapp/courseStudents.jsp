<%@ page import="java.util.List" %>
<%@ page import="Model.User" %>
<%@ page import="DAO.CourseDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Course Students</title>
</head>
<body>
<h2>Students Enrolled in the Course</h2>
<%
  // Instantiate CourseDAO to interact with the database
  CourseDAO courseDAO = new CourseDAO();
  // Retrieve the courseId from the request parameters
  int courseId = Integer.parseInt(request.getParameter("courseId"));
  // Get the list of students enrolled in the specified course
  List<User> students = courseDAO.getStudentsForCourse(courseId);
%>

<table border="1">
  <tr>
    <th>ID</th>
    <th>Username</th>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Phone</th>
    <th>Enter Mark</th>
  </tr>
  <% for(User student : students) { %>
  <tr>
    <td><%= student.getUserId() %></td>
    <td><%= student.getUserName() %></td>
    <td><%= student.getFirstName() %></td>
    <td><%= student.getLastName() %></td>
    <td><%= student.getPhone() %></td>
    <td>
      <!-- Provide a link to a page where marks can be entered for each student -->
      <a href="enterAssessmentMarks.jsp?studentId=<%= student.getUserId() %>&courseId=<%= courseId %>">Enter Marks</a></td>

  </tr>
  <% } %>
</table>
<br>
<!-- A link to navigate back to the teacher dashboard -->
<a href="teacherDashboard.jsp">Back to teacher Dashboard</a>
</body>
</html>
