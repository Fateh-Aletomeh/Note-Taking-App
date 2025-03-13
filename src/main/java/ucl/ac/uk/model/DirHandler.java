package ucl.ac.uk.model;

import java.io.File;
import java.util.ArrayList;


public class DirHandler {
  public void createDir(String dir, String newdirname) {
    System.out.println(dir + "," + newdirname);
    new File(dir, newdirname).mkdirs();
  }

  public ArrayList<String> getDirs(String dirName) {
    File root = new File(dirName);
    ArrayList<String> dirsList = new ArrayList<>();
    File[] files = root.listFiles();

    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) dirsList.add(file.getName());
      }
    }

    return dirsList;
  }
}
