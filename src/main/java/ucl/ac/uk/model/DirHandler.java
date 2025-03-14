package ucl.ac.uk.model;

import java.io.File;
import java.util.ArrayList;


public class DirHandler {
  Indexer indexer = new Indexer();

  public void createDir(String dir, String newdirname) {
    new File(dir, newdirname).mkdirs();
  }

  public void deleteDir(String dir) {
    File directory = new File(dir);
    if (directory.exists() && directory.isDirectory()) {
      deleteRecursively(directory);
    }
    indexer.removeNotesFromDir(dir);
  }

  private void deleteRecursively(File file) {
    File[] allContents = file.listFiles();
    if (allContents != null) {
      for (File f : allContents) {
        deleteRecursively(f);
      }
    }
    file.delete();
  }

  public void renameDir(String dir, String oldDirName, String newDirName) {
    File oldDir = new File(dir, oldDirName);
    File newDir = new File(dir, newDirName);
    if (oldDir.exists() && oldDir.isDirectory()) {
      oldDir.renameTo(newDir);
      indexer.renameDirs(oldDirName, newDirName, dir);
    } else {
      System.out.println("Directory does not exist: " + oldDirName);
    }
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
