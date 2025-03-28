package ucl.ac.uk.servlets;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ucl.ac.uk.model.DirHandler;
import ucl.ac.uk.model.FileHandler;


@WebServlet("/rename")
public class RenameServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    // Recieve data from webpage
    String referer = request.getHeader("referer");
    String newName = request.getParameter("newName");
    String type = request.getParameter("type");

    referer = java.net.URLDecoder.decode(referer, "UTF-8");
    String queryString = referer.split("\\?")[1];
    String[] params = queryString.split("&");
    String dir = null;
    String file = null;

    for (String param : params) {
      String[] keyValue = param.split("=");
      if (keyValue[0].equals("dir")) {
        dir = keyValue[1];
      } else if (keyValue[0].equals("file")) {
        file = keyValue[1];
      }
    }

    if (type.equals("dir")) {
      DirHandler dirhandler = new DirHandler();
      String parentDir = dir.substring(0, dir.lastIndexOf('/'));
      String currDir = dir.substring(dir.lastIndexOf('/') + 1);
      dirhandler.renameDir(parentDir, currDir, newName);

      if (file == null) {
        response.sendRedirect("/dirs?dir=" + parentDir + "/" + newName);
      } else {
        response.sendRedirect("/dirs?dir=" + parentDir + "/" + newName + "&file=" + file);
      }
    } else {
      FileHandler filehandler = new FileHandler();
      filehandler.renameFile(dir, file, newName);

      response.sendRedirect("/dirs?dir=" + dir + "&file=" + newName);
    }
  }
}
