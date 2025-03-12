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
import ucl.ac.uk.model.DirHandler;
import ucl.ac.uk.model.FileHandler;


@WebServlet("/dirs")
public class DirsServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Receive data from webpage
    String currDir = request.getQueryString();
    String dirName = request.getParameter("dir");
    String fileName = request.getParameter("file");

    if (dirName == null) dirName = "";
    if (fileName == null) fileName = "";

    currDir = (currDir == null) ? "" : currDir.substring(4);
    int questionMarkIndex = currDir.indexOf('?');
    if (questionMarkIndex != -1) {
      currDir = currDir.substring(0, questionMarkIndex);
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
    FileHandler filehandler = new FileHandler();
    DirHandler dirhandler = new DirHandler();

    ArrayList<String> files = filehandler.getFiles(dirName);
    ArrayList<String> dirs = dirhandler.getDirs(dirName);
    String fileContent = filehandler.readFile(dirName, fileName);

    // Add the data to request object that is sent to JSP
    request.setAttribute("files", files);
    request.setAttribute("dirs", dirs);
    request.setAttribute("prevDir", prevDir);
    request.setAttribute("currDir", currDir);
    request.setAttribute("fileContent", fileContent);

    // Then forward to JSP
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/dirs.jsp");
    dispatch.forward(request, response);
  }
}
