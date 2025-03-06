<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Notes</title>
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

    </div>
    <div class="note">

    </div>
  </div>
</body>
</html>
