<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
</head>

<body>
<h2>Admin Dashboard</h2>

<!-- Section for creating new users -->
<h3>Create New User</h3>
<!-- The form action is set to a servlet that handles user creation -->
<form action="CreateUserServlet" method="post">
    <label for="userName">Username:</label>
    <input type="text" id="userName" name="userName" required>
    <br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>
    <br>

    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" required>
    <br>

    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" required>
    <br>

    <label for="phone">Phone:</label>
    <input type="text" id="phone" name="phone" required>
    <br>

    <label for="role">Role:</label>
    <select id="role" name="role">
        <option value="student">Student</option>
        <option value="teacher">Teacher</option>
    </select>
    <br>

    <button type="submit">Create User</button>
</form>

<!-- Section for creating new courses -->
<h3>Create New Course</h3>
<!-- The form action is set to a servlet that handles course creation -->
<form action="CreateCourseServlet" method="post">

    <label for="courseName">Course Name:</label>
    <input type="text" id="courseName" name="courseName" required>
    <br>

    <label for="semester">Semester:</label>
    <input type="text" id="semester" name="semester" required>
    <br>

    <button type="submit">Create Course</button>
</form>
<br>


<%-- Check if the successMessage attribute is not null --%>
<% if (session.getAttribute("successMessage") != null) { %>
<div ><%= session.getAttribute("successMessage") %></div>
<% session.removeAttribute("successMessage"); %>
<% } %>

</body>
</html>
