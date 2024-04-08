<%@ page import="java.util.List" %>
<%@ page import="Model.User" %>
<%@ page import="Model.*" %>
<%@ page import="DAO.CourseDAO" %>
<%@ page import="DAO.AssessmentDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Assessment Marks</title>
</head>

<body>
<h2>Assessment Marks</h2>
<%
    // Parse the courseId from the request parameters and the userId from the session
    int courseId = Integer.parseInt(request.getParameter("courseId"));
    int userId = (int) session.getAttribute("userId");

    // Instantiate the AssessmentDAO to interact with the database
    AssessmentDAO assessmentDAO = new AssessmentDAO();
    // Retrieve the list of assessments for the specified course and user
    List<Assessment> assessments = assessmentDAO.getAssessmentsForCourse(userId, courseId);

    // Check if any assessments were found
    if (assessments != null && !assessments.isEmpty()) {
%>

<!-- Create a table to neatly display the assessment marks -->
<table border="1">
    <tr>
        <th>Quiz Marks</th>
        <th>Assignment Marks</th>
        <th>Final Exam Marks</th>
    </tr>
    <!-- Loop through each assessment to display its marks in a table row -->
    <% for(Assessment assessment : assessments) { %>
    <tr>
        <td><%= assessment.getQuizMarks() %></td>
        <td><%= assessment.getAssignmentMarks() %></td>
        <td><%= assessment.getFinalExamMarks() %></td>
    </tr>
    <% } %>
</table>
<%
} else {// Display a message if no marks are available
%>
<p>No marks available to display.</p>
<%
    }
%>
<!-- Link to navigate back to the student dashboard -->
<a href="studentDashboard.jsp">Back to student Dashboard</a>

</body>
</html>
