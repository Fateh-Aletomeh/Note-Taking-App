<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Files</title>
</head>
<body>
  <div class="container main">
    <div class="dirs">
      <%
        ArrayList<String> dirs = (ArrayList<String>) request.getAttribute("dirs");
        for (String dir : dirs) {
          out.println("<a href='files?dir=" + dir + "'>" + dir + "</a>");
        }
      %>
    </div>
    <div class="files">
      <%
        ArrayList<String> files = (ArrayList<String>) request.getAttribute("files");
        for (String file : files) {
          out.println("<a href='notes?file=" + file + "'>" + file + "</a>");
        }
      %>
    </div>
    <div class="note">
      <form action="saveNote" method="post">
        <textarea name="noteContent" rows="10" cols="50"></textarea>
        <br>
        <button type="submit">Save</button>
      </form>
    </div>
  </div>
</body>
</html>
