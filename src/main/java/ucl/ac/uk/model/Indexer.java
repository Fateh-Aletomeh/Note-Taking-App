package ucl.ac.uk.model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;


public class Indexer {
  ObjectMapper mapper = new ObjectMapper();
  File file = new File("Index/index.json");
  ObjectNode index;
  ArrayNode notes;
  
  public Indexer() {
    try {
      index = (ObjectNode) mapper.readTree(file);
      notes = (ArrayNode) index.get("notes");
    } catch (IOException e) {
      System.out.println("Error when trying to read index");
      e.printStackTrace();
    }
  }

  public ArrayList<HashMap<String, String>> getAllNotes() {
    ArrayList<HashMap<String, String>> noteList = new ArrayList<>();
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      HashMap<String, String> noteMap = new HashMap<>();
      note.fields().forEachRemaining(entry -> noteMap.put(entry.getKey(), entry.getValue().asText()));
      noteList.add(noteMap);
    }
    return noteList;
  }

  public void addNote(String name, String path, ArrayList<String> tags) {
    ObjectNode newNote = mapper.createObjectNode();
    newNote.put("name", name);
    newNote.put("path", path);

    String createdAt = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    newNote.put("created_at", createdAt);
    newNote.put("updated_at", createdAt);  // Since updated_at is same as created_at when file is created
    
    ArrayNode tagsNode = mapper.createArrayNode();
    if (tags != null) tags.forEach(tagsNode::add);
    newNote.set("tags", tagsNode);
    notes.add(newNote);

    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, index);
    } catch (IOException e) {
      System.out.println("Error when trying to add note to index");
      e.printStackTrace();
    }
  }

  public void removeNote(String name, String path) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (note.get("name").asText().equals(name) && note.get("path").asText().equals(path)) {
        notes.remove(i);
        break;
      }
    }

    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, index);
    } catch (IOException e) {
      System.out.println("Error when trying to remove note from index");
      e.printStackTrace();
    }
  }

  public void removeNotesFromDir(String dir) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (note.get("path").asText().startsWith(dir)) {
        notes.remove(i);
        i--;
      }
    }

    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, index);
    } catch (IOException e) {
      System.out.println("Error when trying to remove notes from directory in index");
      e.printStackTrace();
    }
  }

  public void renameDirs(String oldName, String newName, String parentDir) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      String notePath = note.get("path").asText();
      System.out.println(parentDir + "/" + oldName);
      if (notePath.startsWith(parentDir + "/" + oldName)) {
        System.out.println(note.get("name"));
        String newPath = notePath.replaceFirst(parentDir + "/" + oldName, parentDir + "/" + newName);
        note.put("path", newPath);
      }
    }

    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, index);
    } catch (IOException e) {
      System.out.println("Error when trying to rename directories in index");
      e.printStackTrace();
    }
  }

  public void renameNote(String oldName, String newName, String path) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (note.get("name").asText().equals(oldName) && note.get("path").asText().equals(path)) {
        note.put("name", newName);
        note.put("updated_at", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        break;
      }
    }

    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, index);
    } catch (IOException e) {
      System.out.println("Error when trying to rename note in index");
      e.printStackTrace();
    }
  }

  public void updateTime(String name, String path) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (note.get("name").asText().equals(name) && note.get("path").asText().equals(path)) {
        note.put("updated_at", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        break;
      }
    }

    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, index);
    } catch (IOException e) {
      System.out.println("Error when trying to rename note in index");
      e.printStackTrace();
    }
  }
}
