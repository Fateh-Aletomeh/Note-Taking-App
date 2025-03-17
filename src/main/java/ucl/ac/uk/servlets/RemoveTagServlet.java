package ucl.ac.uk.servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ucl.ac.uk.model.Indexer;


@WebServlet("/removeTag")
public class RemoveTagServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Receive data from webpage
    String referer = request.getHeader("referer");
    
    // Code to use the model to process something would go here
    Indexer indexer = new Indexer();
    ArrayList<String> tags = indexer.getAllTags();

    // Add the data to request object that is sent to JSP
    request.setAttribute("referer", referer);
    request.setAttribute("tags", tags);

    // Then forward to JSP
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/removeTagPage.jsp");
    dispatch.forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Recieve data from webpage
    String referer = request.getParameter("referer");
    String[] tagsArray = request.getParameterValues("tags");
    
    if (tagsArray != null) {
      ArrayList<String> tags = new ArrayList<>(Arrays.asList(tagsArray));

      // Code to use the model to process something would go here
      Indexer indexer = new Indexer();
      for (String tag : tags) {
        indexer.removeTag(tag);
      }
    }

    // Then return to webpage
    response.sendRedirect(referer);
  }
}
