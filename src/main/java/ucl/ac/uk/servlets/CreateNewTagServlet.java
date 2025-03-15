package ucl.ac.uk.servlets;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ucl.ac.uk.model.Indexer;


@WebServlet("/createNewTag")
public class CreateNewTagServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Receive data from webpage
    String referer = request.getHeader("referer");

    // Add the data to request object that is sent to JSP
    request.setAttribute("referer", referer);

    // Then forward to JSP
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/createNewTagPage.jsp");
    dispatch.forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Recieve data from webpage
    String referer = request.getParameter("referer");
    String tagName = request.getParameter("tagName");

    // Code to use the model to process something would go here
    Indexer indexer = new Indexer();
    indexer.addTag(tagName);

    // Then return to webpage
    response.sendRedirect(referer);
  }
}
