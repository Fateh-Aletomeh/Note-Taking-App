package ucl.ac.uk.model;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;


public class Model {
  public void mkDir(String dirname) {
    new File("Notes", dirname).mkdirs();
  }

  public void createFile(String path, String filename) {
    try {
      File newfile = new File("Notes/" + path, filename);
      newfile.createNewFile();
    } catch (IOException e) {
      System.out.println("An error occurred when trying to create a new file.");
      e.printStackTrace();
    }
  }

  public void renameFile(String dir, String filename, String newName) {
    File file = new File("Notes/" + dir + "/" + filename);
    File newfile = new File("Notes/" + dir + "/" + newName);
    file.renameTo(newfile);
  }

  public ArrayList<String> getDirs(String dirName) {
    File root = new File("Notes/" + dirName);
    ArrayList<String> dirsList = new ArrayList<>();
    File[] files = root.listFiles();

    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) dirsList.add(file.getName());
      }
    }

    return dirsList;
  }

  public ArrayList<String> getFiles(String dirName) {
    File dir = new File("Notes/" + dirName);
    ArrayList<String> filesList = new ArrayList<>();
    File[] files = dir.listFiles();

    if (files != null) {
      for (File file : files) {
        if (file.isFile()) {
          String filename = file.getName();
          filesList.add(filename.substring(0, filename.length() - 4));  // Remove .txt from filename
        }
      }
    }

    return filesList;
  }
}
