package ucl.ac.uk.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;


public class FileHandler {
  public void createFile(String path, String filename) {
    try {
      File newfile = new File(path, filename + ".txt");
      newfile.createNewFile();
    } catch (IOException e) {
      System.out.println("An error occurred when trying to create a new file at: " + path + " called:" + filename + ".txt");
      e.printStackTrace();
    }
  }

  public void deleteFile(String dir, String filename) {
    File file = new File(dir + "/" + filename + ".txt");
    file.delete();
  }

  public void renameFile(String dir, String filename, String newName) {
    File file = new File(dir + "/" + filename + ".txt");
    File newfile = new File(dir + "/" + newName + ".txt");
    file.renameTo(newfile);
  }

  public String readFile(String dir, String filename) {
    if (filename == null) return null;
    StringBuilder content = new StringBuilder();

    try {
      File file = new File(dir + "/" + filename + ".txt");
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        content.append(scanner.nextLine()).append("\n");
      }
      scanner.close();
    } catch (IOException e) {
      System.out.println("An error occurred when trying to read the file: " + dir + "/" + filename + ".txt");
      e.printStackTrace();
    }

    return content.toString();
  }

  public void updateFile(String dir, String filename, String content) {
    try {
      File file = new File(dir + "/" + filename + ".txt");
      FileWriter writer = new FileWriter(file);
      writer.write(content);
      writer.close();
    } catch (IOException e) {
      System.out.println("An error occurred when trying to update the file: " + dir + "/" + filename + ".txt");
      e.printStackTrace();
    }
  }

  public ArrayList<String> getFiles(String dirName) {
    File dir = new File(dirName);
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
