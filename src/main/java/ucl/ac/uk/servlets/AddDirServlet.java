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


@WebServlet("/AddDirServlet")
public class AddDirServlet extends HttpServlet {
  // private static final long serialVersionUID = 1L;

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Recieve data from webpage
    String newDirName = request.getParameter("newDirName");
    String dirName = request.getParameter("dirName");

    // Code to use the model to process something would go here
    Model notes = new Model();
    notes.mkDir(newDirName);

    if (dirName != null) {
      ArrayList<String> files = notes.getFiles(dirName);
      request.setAttribute("files", files);
    }

    ArrayList<String> dirs = notes.getDirs(dirName);
    request.setAttribute("dirs", dirs);

    // Then forward to JSP
    String referer = request.getHeader("referer");
    System.out.println(referer);
    // ServletContext context = getServletContext();
    // RequestDispatcher dispatch = context.getRequestDispatcher(referer);
    // dispatch.forward(request, response);
    response.sendRedirect(referer);
  }
}
