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
      <h1>Create new directory</h1>
      <p>Directory will be created in the following directory: </p>
      
      <%
        String dir = (String) request.getAttribute("dir");
        out.println("<p>" + dir + "</p>");
      %>

      <form action="addDir" method="post">
        <label for="dirName">Name:</label>
        <input type="text" id="dirName" name="dirName" required>
        <br>
        <input type="submit" value="Create Directory">
      </form>

      <a href="dirs?dir=<%= dir %>" class="btn btn-secondary">Cancel</a>
    </div>
  </div>
</body>
</html>
