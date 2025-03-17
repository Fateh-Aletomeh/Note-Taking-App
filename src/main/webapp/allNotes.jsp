<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="ucl.ac.uk.model.Note" %>
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

    <% ArrayList<String> allTags = (ArrayList<String>) request.getAttribute("allTags"); %>

    <% if (allTags != null) { %>
      <form action="/allNotes" method="get">
        <input type="hidden" name="query" value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>">
        <input type="hidden" name="sort" value="<%= request.getParameter("sort") %>">
        <div class="form-group">
          <label for="allTags">Filter by tags:</label>
          <div id="allTags" class="d-flex flex-wrap">
            <% for (String tag : allTags) { %>
              <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="tags" value="<%= tag %>" id="tag_<%= tag %>">
                <label class="form-check-label" for="tag_<%= tag %>"><%= tag %></label>
              </div>
            <% } %>
          </div>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Filter</button>
      </form>
    <% } %>
    
    <br><br>
    <div class="btn-group" role="group" aria-label="Sort options">
      <%
        String query = request.getParameter("query");
        String[] tags = request.getParameterValues("tags");
        ArrayList<String> tagList = new ArrayList<>();
        if (tags != null) {
          tagList.addAll(Arrays.asList(tags));
        }
        
        StringBuilder tagParams = new StringBuilder();
        for (String tag : tagList) {
          tagParams.append("&tags=").append(tag);
        }
      %>
    
      <a href="?<%= (query != null ? "query=" + query + "&" : "") %>sort=asc<%= tagParams.toString() %>" class="btn btn-primary">Alphabetically Ascending</a>
      <a href="?<%= (query != null ? "query=" + query + "&" : "") %>sort=desc<%= tagParams.toString() %>" class="btn btn-primary">Alphabetically Descending</a>
      <a href="?<%= (query != null ? "query=" + query + "&" : "") %>sort=recent<%= tagParams.toString() %>" class="btn btn-primary">Recently Added</a>
      <a href="?<%= (query != null ? "query=" + query + "&" : "") %>sort=oldest<%= tagParams.toString() %>" class="btn btn-primary">Oldest Added</a>
    </div>    
  </nav>

  <div class="noteslist container mt-5">
    <%
      ArrayList<Note> notes = (ArrayList<Note>) request.getAttribute("notes");
      
      if (notes != null && !notes.isEmpty()) {
        // Define ISO format for parsing with truncation of nanoseconds (up to milliseconds)
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat readableFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
        readableFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        for (Note note : notes) {
          String createdAt = note.getCreatedAt();
          String updatedAt = note.getUpdatedAt();

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
          out.println("<h4 class='card-title'>" + note.getName() + "</h4>");
          out.println("<p class='card-text'><strong>Directory:</strong> " + note.getPath() + "</p>");
          out.println("</div>");
          out.println("<div class='col-md-6'>");
          out.println("<p class='card-text'><strong>Date Created:</strong> " + readableFormat.format(createdDate) + "</p>");
          out.println("<p class='card-text'><strong>Last Updated:</strong> " + readableFormat.format(updatedDate) + "</p>");
          out.println("</div>");
          out.println("</div>");
          out.println("<br><a href='dirs?dir=" + note.getPath() + "&file=" + note.getName() + "' class='btn btn-info'>Open Note</a>");
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
