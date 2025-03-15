<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.TimeZone" %>


<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Notes</title>
</head>
<body class="allnotes">
  <nav style="text-align: center;" class="mt-4">
    <a href="dirs?dir=Notes" class="btn btn-warning">Back to directory view</a>
    <br><br>
    <form action="/allNotes" method="get" class="d-inline-block">
      <input type="text" name="query" class="form-control d-inline-block" style="width: auto; display: inline-block;" placeholder="Search notes...">
      <button type="submit" class="btn btn-secondary">Search</button>
    </form>
    <br><br>
    <div class="btn-group" role="group" aria-label="Sort options">
      <a href="?sort=asc" class="btn btn-primary">Alphabetically Ascending</a>
      <a href="?sort=desc" class="btn btn-primary">Alphabetically Descending</a>
      <a href="?sort=recent" class="btn btn-primary">Recently Added</a>
      <a href="?sort=oldest" class="btn btn-primary">Oldest Added</a>
    </div>
  </nav>

  <div class="noteslist container mt-5">
    <%
      ArrayList<HashMap<String, String>> notes = (ArrayList<HashMap<String, String>>) request.getAttribute("notes");
      
      if (notes != null && !notes.isEmpty()) {
        // Define ISO format for parsing with truncation of nanoseconds (up to milliseconds)
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat readableFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
        readableFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        for (HashMap<String, String> note : notes) {
          String createdAt = note.get("created_at");
          String updatedAt = note.get("updated_at");

          // Remove any extra nanoseconds and handle the "Z" for UTC
          createdAt = createdAt.substring(0, 23) + "Z";
          updatedAt = updatedAt.substring(0, 23) + "Z";

          Date createdDate = null;
          Date updatedDate = null;
          try {
            createdDate = isoFormat.parse(createdAt);
            updatedDate = isoFormat.parse(updatedAt);
          } catch (ParseException e) {
            e.printStackTrace();
          }

          out.println("<div class='card mb-3'>");
          out.println("<div class='card-body'>");
            out.println("<div class='row'>");
            out.println("<div class='col-md-6'>");
            out.println("<h4 class='card-title'>" + note.get("name") + "</h4>");
            out.println("<p class='card-text'><strong>Directory:</strong> " + note.get("path") + "</p>");
            out.println("</div>");
            out.println("<div class='col-md-6'>");
            out.println("<p class='card-text'><strong>Date Created:</strong> " + readableFormat.format(createdDate) + "</p>");
            out.println("<p class='card-text'><strong>Last Updated:</strong> " + readableFormat.format(updatedDate) + "</p>");
            out.println("</div>");
            out.println("</div>");
          out.println("<br><a href='dirs?dir=" + note.get("path") + "&file=" + note.get("name") + "' class='btn btn-info'>Open Note</a>");
          out.println("</div>");
          out.println("</div>");
        }
      } else {
        out.println("<p class='alert alert-warning'>No notes available.</p>");
      }
    %>
  </div>
</body>
</html>
