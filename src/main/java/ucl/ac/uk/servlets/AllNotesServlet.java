package ucl.ac.uk.servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.model.Indexer;


@WebServlet("/allNotes")
public class AllNotesServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Receive data from webpage
    String sortOrder = request.getParameter("sortOrder");
    String query = request.getParameter("query");

    // Code to use the model to process something would go here
    Indexer indexer = new Indexer();
    if (query == null) {
      ArrayList<HashMap<String, String>> notes = indexer.getAllNotes();
    } else {
      
    }

    // Add the data to request object that is sent to JSP
    request.setAttribute("notes", notes);

    // Then forward to JSP
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/allNotes.jsp");
    dispatch.forward(request, response);
  }
}
