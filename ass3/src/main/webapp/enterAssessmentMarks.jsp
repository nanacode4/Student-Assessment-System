<%@ page import="Model.User" %>
<%@ page import="Model.Course" %>
<%@ page import="DAO.UserDAO" %>
<%@ page import="DAO.CourseDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Enter Assessment Marks</title>
</head>

<body>
<h2>Enter Assessment Marks</h2>
<%
    // Instantiate DAO objects for accessing user and course data
    UserDAO userDAO = new UserDAO();
    CourseDAO courseDAO = new CourseDAO();
    // Retrieve student and course IDs from request parameters
    int studentId = Integer.parseInt(request.getParameter("studentId"));
    int courseId = Integer.parseInt(request.getParameter("courseId"));

    // Fetch student and course details from the database
    User student = userDAO.getUserById(studentId);
    Course course = courseDAO.getCourseById(courseId);
%>

<h3>Assessments for <%= student.getFirstName() %>
    <%= student.getLastName() %> in the course: <%= course.getCourseName() %></h3>

<!-- Form for submitting assessment marks to the UpdateAssessmentServlet -->
<form action="UpdateAssessmentServlet" method="post">
    <input type="hidden" name="studentId" value="<%= student.getUserId() %>" />
    <input type="hidden" name="courseId" value="<%= course.getCourseId() %>" />

    <label for="quizMarks">Quiz Marks:</label>
    <input type="number" name="quizMarks" id="quizMarks" required /><br/>

    <label for="assignmentMarks">Assignment Marks:</label>
    <input type="number" name="assignmentMarks" id="assignmentMarks" required /><br/>

    <label for="finalExamMarks">Final Exam Marks:</label>
    <input type="number" name="finalExamMarks" id="finalExamMarks" required /><br/>

    <button type="submit">Submit Marks</button>
</form>

<!-- Link to navigate back to the list of students enrolled in the course -->
<a href="courseStudents.jsp?courseId=<%= courseId %>">Back to Student List</a>
</body>
</html>
