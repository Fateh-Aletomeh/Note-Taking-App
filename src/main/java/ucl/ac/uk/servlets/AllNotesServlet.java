package ucl.ac.uk.servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ucl.ac.uk.model.Note;
import ucl.ac.uk.model.Indexer;
import ucl.ac.uk.model.FileHandler;


@WebServlet("/allNotes")
public class AllNotesServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Receive data from webpage
    String sortOrder = request.getParameter("sort");
    String query = request.getParameter("query");
    String[] tags = request.getParameterValues("tags");
    ArrayList<String> tagList = new ArrayList<>();
    if (tags != null) {
      tagList.addAll(Arrays.asList(tags));
    }

    // Code to use the model to process something would go here
    Indexer indexer = new Indexer();
    FileHandler filehandler = new FileHandler();
    ArrayList<Note> notes;
    ArrayList<String> allTags = indexer.getAllTags();

    if (query == null) {
      notes = indexer.getAllNotes();
    } else {
      notes = filehandler.searchFiles(query);
      notes = indexer.filterBasedOnTags(notes, tagList);
    }

    // Sort notes
    if (sortOrder != null) {
      switch (sortOrder) {
        case "asc":
          notes.sort(Comparator.comparing(note -> note.getName()));
          break;
        case "desc":
          notes.sort(Comparator.comparing((Note note) -> note.getName()).reversed());
          break;
        case "recent":
          notes.sort(Comparator.comparing((Note note) -> note.getCreatedAt()).reversed());
          break;
        case "oldest":
          notes.sort(Comparator.comparing(note -> note.getCreatedAt()));
      }
    }

    // Add the data to request object that is sent to JSP
    request.setAttribute("notes", notes);
    request.setAttribute("allTags", allTags);

    // Then forward to JSP
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/allNotes.jsp");
    dispatch.forward(request, response);
  }
}
