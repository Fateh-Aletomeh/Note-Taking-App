<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>


<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Notes</title>
</head>
<body>
  <div class="cont main" id="home">
    <div>
      <h1>Remove tags</h1>

      <% 
        String referer = (String) request.getAttribute("referer");
        ArrayList<String> tags = (ArrayList<String>) request.getAttribute("tags");
      %>

      <form action="/removeTag" method="post">
        <input type="hidden" name="referer" value="<%= referer %>">
        <div class="form-group">
          <ul id="tags" class="list-group"></ul>
        <% for (String tag : tags) { %>
        <input type="checkbox" name="tags" value="<%= tag %>" id="tag_<%= tag %>">
        <label for="tag_<%= tag %>"><%= tag %></label>
          </li>
        <% } %>
          </ul>
        </div>
        <button type="submit" class="btn btn-danger mt-3">Remove Selected Tags</button>
      </form>

      <a href="<%= referer %>" class="btn btn-secondary mt-3">Cancel</a>
    </div>
  </div>
</body>
</html>
