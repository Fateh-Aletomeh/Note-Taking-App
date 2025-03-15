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
  ArrayNode tags;
  
  public Indexer() {
    try {
      index = (ObjectNode) mapper.readTree(file);
      notes = (ArrayNode) index.get("notes");
      tags = (ArrayNode) index.get("tags");
    } catch (IOException e) {
      System.out.println("Error when trying to read index");
      e.printStackTrace();
    }
  }

  private boolean matchesNote(ObjectNode note, String name, String path) {
    return note.get("name").asText().equals(name) && note.get("path").asText().equals(path);
  }

  private void writeChangesToIndex() {
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, index);
    } catch (IOException e) {
      System.out.println("Error when trying to make changes to index");
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

    writeChangesToIndex();
  }

  public void removeNote(String name, String path) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (matchesNote(note, name, path)) {
        notes.remove(i);
        break;
      }
    }
    writeChangesToIndex();
  }

  public void removeNotesFromDir(String dir) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (note.get("path").asText().startsWith(dir)) {
        notes.remove(i);
        i--;
      }
    }
    writeChangesToIndex();
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
    writeChangesToIndex();
  }

  public void renameNote(String oldName, String newName, String path) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (matchesNote(note, oldName, path)) {
        note.put("name", newName);
        note.put("updated_at", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        break;
      }
    }
    writeChangesToIndex();
  }

  public void updateTime(String name, String path) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (matchesNote(note, name, path)) {
        note.put("updated_at", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        break;
      }
    }
    writeChangesToIndex();
  }

  public void addTag(String tag) {
    if (!tags.has(tag)) {
      tags.add(tag);
      writeChangesToIndex();
    }
  }

  public void removeTag(String tag) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      ArrayNode tagsNode = (ArrayNode) note.get("tags");
      for (int j = 0; j < tagsNode.size(); j++) {
        if (tagsNode.get(j).asText().equals(tag)) {
          tagsNode.remove(j);
          j--;
        }
      }
      note.set("tags", tagsNode);
    }

    for (int i = 0; i < tags.size(); i++) {
      if (tags.get(i).asText().equals(tag)) {
        tags.remove(i);
        break;
      }
    }
    writeChangesToIndex();
  }

  public void addTagToNote(String name, String path, String tag) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (matchesNote(note, name, path)) {
        ArrayNode tagsNode = (ArrayNode) note.get("tags");
        tagsNode.add(tag);
        note.set("tags", tagsNode);
      }
    }
    writeChangesToIndex();
  }

  public void removeTagFromNote(String name, String path, String tag) {
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (matchesNote(note, name, path)) {
        ArrayNode tagsNode = (ArrayNode) note.get("tags");
        for (int j = 0; j < tagsNode.size(); j++) {
          if (tagsNode.get(j).asText().equals(tag)) {
            tagsNode.remove(j);
            break;
          }
        }
        note.set("tags", tagsNode);
      }
    }
    writeChangesToIndex();
  }

  public ArrayList<String> getTags(String name, String path) {
    ArrayList<String> noteTags = new ArrayList<>();
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode note = (ObjectNode) notes.get(i);
      if (matchesNote(note, name, path)) {
        ArrayNode tagsNode = (ArrayNode) note.get("tags");
        tagsNode.forEach(tag -> noteTags.add(tag.asText()));
        break;
      }
    }
    return noteTags;
  }

  public ArrayList<String> getOtherTags(ArrayList<String> notesTags) {
    ArrayList<String> otherTags = new ArrayList<>();
    for (int i = 0; i < tags.size(); i++) {
      String tag = tags.get(i).asText();
      if (!notesTags.contains(tag)) {
        otherTags.add(tag);
      }
    }
    return otherTags;
  }
}
