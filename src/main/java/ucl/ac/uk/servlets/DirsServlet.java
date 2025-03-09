package ucl.ac.uk.servlets;

import java.io.*;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ucl.ac.uk.model.Model;


@WebServlet("/dirs")
public class DirsServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Recieve data from webpage
    String currDir = request.getQueryString();
    String dirName = request.getParameter("dir");

    if (dirName == null) dirName = "";
    if (currDir == null) {
      currDir = "";
    } else {
      currDir = currDir.substring(4);
    }

    String prevDir;
    if (currDir.isEmpty()) {  // This means we are in the Notes directory
      prevDir = null;
    } else if (!currDir.contains("/")) {  // This means we are in a directory whose parent is Notes
      prevDir = "";
    } else {
      int lastSlashIndex = currDir.lastIndexOf('/', currDir.length() - 2);
      if (lastSlashIndex == -1) {
        prevDir = "";
      } else {
        prevDir = currDir.substring(0, lastSlashIndex + 1);
      }
    }

    // Code to use the model to process something would go here
    Model model = new Model();
    ArrayList<String> files = model.getFiles(dirName);
    ArrayList<String> dirs = model.getDirs(dirName);

    // Add the data to request object that is sent to JSP
    request.setAttribute("files", files);
    request.setAttribute("dirs", dirs);
    request.setAttribute("prevDir", prevDir);
    request.setAttribute("currDir", currDir);

    // Then forward to JSP
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/dirs.jsp");
    dispatch.forward(request, response);
  }
}
