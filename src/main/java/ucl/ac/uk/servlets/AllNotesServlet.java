package ucl.ac.uk.servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.SortedMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.model.Indexer;
import ucl.ac.uk.model.FileHandler;


@WebServlet("/allNotes")
public class AllNotesServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Receive data from webpage
    String sortOrder = request.getParameter("sort");
    String query = request.getParameter("query");

    // Code to use the model to process something would go here
    Indexer indexer = new Indexer();
    FileHandler filehandler = new FileHandler();
    ArrayList<HashMap<String, String>> notes;

    if (query == null) {
      notes = indexer.getAllNotes();
    } else {
      notes = filehandler.searchFiles(query);
    }

    // Sort notes
    if (sortOrder != null) {
      switch (sortOrder) {
        case "asc":
          notes.sort(Comparator.comparing(note -> note.get("name")));
          break;
        case "desc":
          notes.sort(Comparator.comparing((HashMap<String, String> note) -> note.get("name")).reversed());
          break;
        case "recent":
          notes.sort(Comparator.comparing((HashMap<String, String> note) -> note.get("created_at")).reversed());
          break;
        case "oldest":
          notes.sort(Comparator.comparing(note -> note.get("created_at")));
      }
    }

    // Add the data to request object that is sent to JSP
    request.setAttribute("notes", notes);

    // Then forward to JSP
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/allNotes.jsp");
    dispatch.forward(request, response);
  }
}
