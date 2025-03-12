<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Notes</title>
</head>
<body>
  <div class="container main" id="home">
    <div>
      <h1>Create new note</h1>
      <p>Note will be created in the following directory: </p>
      
      <%
        String dir = (String) request.getAttribute("dir");
        out.println("<p>" + dir + "</p>");
      %>

      <form action="addFile" method="post">
        <label for="noteName">Name:</label>
        <input type="text" id="noteName" name="noteName" required>
        <br>
        <label for="noteContent">Content:</label>
        <textarea id="noteContent" name="noteContent" rows="4" cols="50" required></textarea>
        <br>
        <input type="submit" value="Create Note">
      </form>

      <a href="dirs?dir=<%= dir %>" class="btn btn-secondary">Cancel</a>
    </div>
  </div>
</body>
</html>
