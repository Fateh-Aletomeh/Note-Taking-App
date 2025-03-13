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
        String currDir = (String) request.getAttribute("currDir");
        
        if (prevDir != null) {
          String prevDirName = prevDir.substring(prevDir.lastIndexOf('/') + 1);
          out.println("<a href='dirs?dir=" + prevDir + "'><svg xmlns='http://www.w3.org/2000/svg' width='22' height='22' fill='currentColor' class='bi bi-arrow-left-circle' viewBox='0 0 16 16'><path fill-rule='evenodd' d='M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8m15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0m-4.5-.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5z'/></svg> " + prevDirName + "</a>");
        }

        ArrayList<String> dirs = (ArrayList<String>) request.getAttribute("dirs");
        for (String dir : dirs) {
          out.println("<a href='dirs?dir=" + currDir + "/" + dir + "'>" + dir + "</a>");
        }
      %>

      <a href="addDir?dir=<%= currDir %>" class="btn btn-primary">
        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
          <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
          <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zm0 1H2v12h12V2z"/>
        </svg>
        New Directory
      </a>
    </div>
    <div class="files">
      <%
        ArrayList<String> files = (ArrayList<String>) request.getAttribute("files");

        if (files != null) {
          for (String file : files) {
            out.println("<a href='dirs?dir=" + currDir + "&file=" + file + "'>" + file + "</a>");
          }
        }
      %>

      <a href="addFile?dir=<%= currDir %>" class="btn btn-primary">
        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
          <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
          <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zm0 1H2v12h12V2z"/>
        </svg>
        New Note
      </a>
    </div>
    <div class="note">
      <%
        String fileContent = (String) request.getAttribute("fileContent");
        
        if (fileContent != null) {
          out.println("<form action='SaveFileServlet' method='post'>"
                    + "<textarea name='noteContent' rows='10' cols='50'>" + fileContent + "</textarea>"
                    + "<br>"
                    + "<button type='submit' class='addDir'>Save</button>"
                    + "</form>");
        }
      %>
    </div>
  </div>
</body>
</html>
