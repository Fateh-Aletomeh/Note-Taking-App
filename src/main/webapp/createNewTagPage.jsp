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
      <h1>Create new tag</h1>

      <% String referer = (String) request.getAttribute("referer"); %>

      <form action="createNewTag" method="post">
        <label for="tagName">Name:</label>
        <br>
        <input type="text" id="tagName" name="tagName" required>
        <input type="hidden" name="referer" value="<%= referer %>">
        <br>
        <button type="submit" class="btn btn-primary mt-3">Create Tag</button>
      </form>

      <a href="referer" class="btn btn-secondary mt-3">Cancel</a>
    </div>
  </div>
</body>
</html>
