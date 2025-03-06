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


@WebServlet("/files")
public class FilesServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Recieve data from webpage
    String dirName = request.getParameter("dir");

    // Code to use the model to process something would go here
    Model notes = new Model();
    ArrayList<String> files = notes.getFiles(dirName);
    ArrayList<String> dirs = notes.getDirs();

    // Add the data to request object that is sent to JSP
    request.setAttribute("files", files);
    request.setAttribute("dirs", dirs);

    // Then forward to JSP
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/files.jsp");
    dispatch.forward(request, response);
  }
}
