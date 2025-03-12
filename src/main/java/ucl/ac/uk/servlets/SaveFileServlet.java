package ucl.ac.uk.servlets;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ucl.ac.uk.model.FileHandler;


@WebServlet("/SaveFileServlet")
public class SaveFileServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Recieve data from webpage
    String referer = request.getHeader("referer");
    String fileContent = request.getParameter("noteContent");

    // Parse data
    String queryString = referer.split("\\?")[1];
    queryString = java.net.URLDecoder.decode(queryString, "UTF-8");
    String[] params = queryString.split("&");
    String dir = null;
    String file = null;

    for (String param : params) {
      String[] keyValue = param.split("=");
      if (keyValue[0].equals("dir")) {
        dir = keyValue[1];
      } else if (keyValue[0].equals("file")) {
        file = keyValue[1] + ".txt";
      }
    }

    // Code to use the model to process something would go here
    FileHandler filehandler = new FileHandler();
    filehandler.updateFile(dir, file, fileContent);

    // Then return to webpage
    response.sendRedirect(referer);
  }
}
