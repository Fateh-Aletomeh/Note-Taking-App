package ucl.ac.uk.servlets;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ucl.ac.uk.model.FileHandler;


@WebServlet("/addFile")
public class AddFileServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Receive data from webpage
    String dirName = request.getParameter("dir");

    // Add the data to request object that is sent to JSP
    request.setAttribute("dir", dirName);

    // Then forward to JSP
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/addFilePage.jsp");
    dispatch.forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Recieve data from webpage
    String referer = request.getHeader("referer");
    String fileName = request.getParameter("noteName");
    String fileContent = request.getParameter("noteContent");

    // Parse data
    String dir = referer.substring(referer.indexOf("dir=") + 4);
    dir = java.net.URLDecoder.decode(dir, "UTF-8");

    // Code to use the model to process something would go here
    FileHandler filehandler = new FileHandler();
    filehandler.createFile(dir, fileName);
    filehandler.updateFile(dir, fileName, fileContent);

    // Then return to webpage
    response.sendRedirect("/dirs?dir=" + dir);
  }
}
