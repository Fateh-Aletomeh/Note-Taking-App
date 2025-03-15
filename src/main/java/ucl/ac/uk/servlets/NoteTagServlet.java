package ucl.ac.uk.servlets;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ucl.ac.uk.model.Indexer;


@WebServlet("/noteTag")
public class NoteTagServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Recieve data from webpage
    String referer = request.getHeader("referer");
    String tagName = request.getParameter("tagName");
    String action = request.getParameter("action");
    String filename = request.getParameter("filename");
    String dir = request.getParameter("dir");

    System.out.println(tagName);
    System.out.println(filename);
    System.out.println(dir);

    // Code to use the model to process something would go here
    Indexer indexer = new Indexer();
    if (action.equals("add")) {
      indexer.addTagToNote(filename, dir, tagName);
    } else {
      indexer.removeTagFromNote(filename, dir, tagName);
    }

    // Then return to webpage
    response.sendRedirect(referer);
  }
}
