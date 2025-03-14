<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Notes</title>
</head>
<body>
  <div class="cont main" id="home">
    <div>
      <h1>Create new note</h1>
      <p>Note will be created in the following directory: </p>
      
      <%
        String dir = (String) request.getAttribute("dir");
        out.println("<p>" + dir + "</p>");
      %>

      <form action="addFile" method="post">
        <label for="noteName">Name:</label>
        <br>
        <input type="text" id="noteName" name="noteName" required>
        <br>
        <label for="noteContent">Content:</label>
        <br>
        <textarea id="noteContent" name="noteContent" rows="4" cols="50" required></textarea>
        <br>
        <button type="submit" class="btn btn-primary mt-3">Create Note</button>
      </form>

      <a href="dirs?dir=<%= dir %>" class="btn btn-secondary mt-3">Cancel</a>
    </div>
  </div>
</body>
</html>
