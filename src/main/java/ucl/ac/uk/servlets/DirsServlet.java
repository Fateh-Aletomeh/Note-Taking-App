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
    String currDir = request.getParameter("dir");
    String currFile = request.getParameter("file");
    String prevDir;

    if (currDir.equals("Notes")) {
      prevDir = null;
    } else if (currDir.matches("^[^/]+/[^/]+$")) {
      prevDir = "Notes";
    } else {
      prevDir = currDir.substring(0, currDir.lastIndexOf('/'));
    }

    // Code to use the model to process something would go here
    FileHandler filehandler = new FileHandler();
    DirHandler dirhandler = new DirHandler();

    ArrayList<String> files = filehandler.getFiles(currDir);
    ArrayList<String> dirs = dirhandler.getDirs(currDir);
    String fileContent = filehandler.readFile(currDir, currFile);

    // Add the data to request object that is sent to JSP
    request.setAttribute("files", files);
    request.setAttribute("dirs", dirs);
    request.setAttribute("prevDir", prevDir);
    request.setAttribute("currDir", currDir);
    request.setAttribute("currFile", currFile);
    request.setAttribute("fileContent", fileContent);

    // Then forward to JSP
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/dirs.jsp");
    dispatch.forward(request, response);
  }
}
