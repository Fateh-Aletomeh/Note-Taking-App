package ucl.ac.uk.model;

import java.util.ArrayList;

public class Note {
  String name;
  String path;
  String created_at;
  String updated_at;
  ArrayList<String> tags;

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  public String getCreatedAt() {
    return created_at;
  }

  public String getUpdatedAt() {
    return updated_at;
  }

  public ArrayList<String> getTags() {
    return tags;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setCreatedAt(String created_at) {
    this.created_at = created_at;
  }

  public void setUpdatedAt(String updated_at) {
    this.updated_at = updated_at;
  }

  public void setTags(ArrayList<String> tags) {
    this.tags = tags;
  }
}
