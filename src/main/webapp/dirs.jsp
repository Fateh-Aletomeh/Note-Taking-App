<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>


<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Notes</title>
</head>
<body>
  <div class="cont main">
    <div class="dirs">
      <%
        String prevDir = (String) request.getAttribute("prevDir");
        String currDir = (String) request.getAttribute("currDir");
        
        if (prevDir != null) {
          String prevDirName = prevDir.substring(prevDir.lastIndexOf('/') + 1);
            out.println("<a href='dirs?dir=" + prevDir + "' class='btn btn-secondary d-flex align-items-center mb-2'>"
            + "<svg xmlns='http://www.w3.org/2000/svg' width='22' height='22' fill='currentColor' class='bi bi-arrow-left-circle me-2' viewBox='0 0 16 16'>"
            + "<path fill-rule='evenodd' d='M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8m15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0m-4.5-.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5z'/>"
            + "</svg> " + prevDirName + "</a>");
        }

        ArrayList<String> dirs = (ArrayList<String>) request.getAttribute("dirs");
        for (String dir : dirs) {
            out.println("<a href='dirs?dir=" + currDir + "/" + dir + "' class='btn btn-outline-primary d-flex align-items-center mb-2'>" + dir + "</a>");
        }
      %>

      <a href="addDir?dir=<%= currDir %>" class="btn btn-primary d-flex align-items-center">
        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-plus-square me-2" viewBox="0 0 16 16">
          <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
          <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zm0 1H2v12h12V2z"/>
        </svg>
        New Directory
      </a></svg>
    </div>
    <div class="files">
      <%
        ArrayList<String> files = (ArrayList<String>) request.getAttribute("files");

        if (files != null) {
          for (String file : files) {
            out.println("<a href='dirs?dir=" + currDir + "&file=" + file + "' class='btn btn-outline-info d-flex align-items-center mb-2'>" + file + "</a>");
          }
        }
      %>

      <a href="addFile?dir=<%= currDir %>" class="btn btn-info d-flex align-items-center">
        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-plus-square me-2" viewBox="0 0 16 16">
          <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
          <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zm0 1H2v12h12V2z"/>
        </svg>
        New Note
      </a></svg>
    </div>
    <div class="note">
      <div class="w-100 text-center mb-3">
        <a href="/allNotes?query=&sort=asc" class="btn btn-warning">View all notes</a>
      </div>

      <%
        String currDirName = currDir.contains("/") ? currDir.substring(currDir.lastIndexOf('/') + 1) : currDir;

        if (!currDirName.equals("Notes")) {
          out.println("<form action='rename' method='post' class='d-inline-block me-2 mb-2'>"
            + "<label for='newName' class='form-label me-2'><b>Current directory: </b>" + currDir + "</label>"
            + "<input type='text' id='newName' name='newName' value='" + currDirName + "' class='form-control d-inline-block w-auto me-2'>"
            + "<input type ='hidden' name='type' value='dir'>"
            + "<button type='submit' class='btn btn-primary me-2'>Rename</button>"
            + "</form>"
            + "<form action='delete' method='post' class='d-inline-block mb-2'>"
            + "<input type ='hidden' name='type' value='dir'>"
            + "<button type='submit' class='btn btn-danger'>Delete</button>"
            + "</form>");
        } else {
          out.println("<p><b>Current directory:</b> Notes</p>");
        }

        out.println("<br>");
        String currFileName = (String) request.getAttribute("currFile");
        String fileContent = (String) request.getAttribute("fileContent");

        if (fileContent != null) {
          out.println("<form action='rename' method='post' class='d-inline-block me-2 mb-2'>"
            + "<label for='newName' class='form-label me-2'><b>Current file: </b>" + currFileName + "</label>"
            + "<input type='text' id='newName' name='newName' value='" + currFileName + "' class='form-control d-inline-block w-auto me-2'>"
            + "<input type ='hidden' name='type' value='file'>"
            + "<button type='submit' class='btn btn-primary me-2'>Rename</button>"
            + "</form>"
            + "<form action='delete' method='post' class='d-inline-block mb-2'>"
            + "<input type ='hidden' name='type' value='file'>"
            + "<button type='submit' class='btn btn-danger'>Delete</button>"
            + "</form>");

          out.println("<form action='SaveFileServlet' method='post'>"
            + "<textarea name='noteContent' rows='10' cols='40' class='form-control mb-2'>" + fileContent + "</textarea>"
            + "<button type='submit' class='btn btn-success'>Save</button>"
            + "</form><br>");
        }

        ArrayList<String> tagsYes = (ArrayList<String>) request.getAttribute("tagsYes");
        ArrayList<String> tagsNo = (ArrayList<String>) request.getAttribute("tagsNo");
      %>

      <%
        if (currFileName != null) {
      %>
      <div class="tags">
        <div class="row mb-3">
          <div class="col">
        <p><b>Tags in Note:</b></p>
        <%
          if (tagsYes != null) {
            for (String tag : tagsYes) {
              out.println("<form action='/noteTag' method='post' class='d-inline-block me-2 mb-2'>"
              + "<span class='badge bg-primary'>" + tag + "</span>"
              + "<input type='hidden' name='tagName' value='" + tag + "'>"
              + "<input type='hidden' name='action' value='remove'>"
              + "<input type='hidden' name='filename' value='" + currFileName + "'>"
              + "<input type='hidden' name='dir' value='" + currDir + "'>"
              + "<button type='submit' class='btn btn-danger btn-sm ms-1'>x</button>"
              + "</form>");
            }
          }
        %>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <p><b>Tags not in Note:</b></p>
            <%
              if (tagsNo != null) {
                for (String tag : tagsNo) {
                  out.println("<form action='/noteTag' method='post' class='d-inline-block me-2 mb-2'>"
                  + "<span class='badge bg-secondary p-2'>" + tag + "</span>"
                  + "<input type='hidden' name='tagName' value='" + tag + "'>"
                  + "<input type='hidden' name='action' value='add'>"
                  + "<input type='hidden' name='filename' value='" + currFileName + "'>"
                  + "<input type='hidden' name='dir' value='" + currDir + "'>"
                  + "<button type='submit' class='btn btn-success btn-sm ms-1 p-2'>+</button>"
                  + "</form>");
                }
              }
            %>
            <div class="w-100 text-center mt-3">
              <a href="/createNewTag" class="btn btn-primary me-2">Create New Tag</a>
              <a href="/removeTag" class="btn btn-danger">Remove Tags</a>
            </div>
          </div>
        </div>
      </div>
      <%
        }
      %>
    </div>
  </div>
</body>
</html>
