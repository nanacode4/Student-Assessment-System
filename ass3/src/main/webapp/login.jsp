<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>

<%-- Check for any login error messages --%>
<% if(request.getAttribute("loginError") != null) { %>
<div ><%= request.getAttribute("loginError") %></div>
<% } %>

<!-- The login form that users will interact with -->
<!-- Form data is sent to LoginServlet using POST method -->
<form action="LoginServlet" method="post">
    <div>
        <label for="userName">Username:</label>
        <input type="text" id="userName" name="userName" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <button type="submit">Login</button>
    </div>
</form>


</body>
</html>