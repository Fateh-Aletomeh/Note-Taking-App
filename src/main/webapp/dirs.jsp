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
        String prevDir = (String) request.getAttribute("prevDir");
        
        if (prevDir != null) {
          if (prevDir.isEmpty()) {
            // If previous directory is Notes
            out.println("<a href='dirs'> Notes </a>");
          } else {
            String prevDirName = prevDir.substring(prevDir.lastIndexOf('/', prevDir.length() - 2) + 1, prevDir.length() - 1);
            out.println("<a href='dirs?dir=" + prevDir + "'>" + prevDirName + "</a>");
          }
        }

        String currDir = (String) request.getAttribute("currDir");
        if (!currDir.isEmpty() && currDir.charAt(currDir.length() - 1) != '/') currDir += "/";

        ArrayList<String> dirs = (ArrayList<String>) request.getAttribute("dirs");
        for (String dir : dirs) {
          out.println("<a href='dirs?dir=" + currDir + dir + "'>" + dir + "</a>");
        }
      %>
    </div>
    <div class="files">
      <%
        ArrayList<String> files = (ArrayList<String>) request.getAttribute("files");
        if (files != null) {
          for (String file : files) {
            out.println("<a href='notes?file=" + file + "'>" + file + "</a>");
          }
        }
      %>
    </div>
    <div class="note">

    </div>
  </div>
</body>
</html>
