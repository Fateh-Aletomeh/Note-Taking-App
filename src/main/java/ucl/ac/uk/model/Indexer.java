package ucl.ac.uk.model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

  private boolean matchesNote(Note note, String name, String path) {
    return note.getName().equals(name) && note.getPath().equals(path);
  }

  private void writeChangesToIndex() {
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, index);
    } catch (IOException e) {
      System.out.println("Error when trying to make changes to index");
      e.printStackTrace();
    }
  }

  public ArrayList<String> getAllTags() {
    ArrayList<String> tagList = new ArrayList<>();
    for (int i = 0; i < tags.size(); i++) {
      tagList.add(tags.get(i).asText());
    }
    return tagList;
  }

  public ArrayList<Note> getAllNotes() {
    ArrayList<Note> noteList = new ArrayList<>();
    for (int i = 0; i < notes.size(); i++) {
      ObjectNode noteNode = (ObjectNode) notes.get(i);
      Note note = mapper.convertValue(noteNode, Note.class);
      noteList.add(note);
    }
    return noteList;
  }

  public void addNote(String name, String path, ArrayList<String> tags) {
    Note newNote = new Note();
    newNote.setName(name);
    newNote.setPath(path);

    String createdAt = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    newNote.setCreatedAt(createdAt);
    newNote.setUpdatedAt(createdAt);  // Since updated_at is same as created_at when file is created

    if (tags == null) {
      tags = new ArrayList<>();
    }
    newNote.setTags(tags);
    notes.add(mapper.convertValue(newNote, ObjectNode.class));

    writeChangesToIndex();
  }

    public void removeNote(String name, String path) {
    for (int i = 0; i < notes.size(); i++) {
      Note note = mapper.convertValue(notes.get(i), Note.class);
      if (matchesNote(note, name, path)) {
        notes.remove(i);
        break;
      }
    }
    writeChangesToIndex();
  }

  public void removeNotesFromDir(String dir) {
    for (int i = 0; i < notes.size(); i++) {
      Note note = mapper.convertValue(notes.get(i), Note.class);
      if (note.getPath().startsWith(dir)) {
        notes.remove(i);
        i--;
      }
    }
    writeChangesToIndex();
  }

  public void renameDirs(String oldName, String newName, String parentDir) {
    for (int i = 0; i < notes.size(); i++) {
      Note note = mapper.convertValue(notes.get(i), Note.class);
      String notePath = note.getPath();
      if (notePath.startsWith(parentDir + "/" + oldName)) {
        String newPath = notePath.replaceFirst(parentDir + "/" + oldName, parentDir + "/" + newName);
        note.setPath(newPath);
        notes.set(i, mapper.convertValue(note, ObjectNode.class));
      }
    }
    writeChangesToIndex();
  }

  public void renameNote(String oldName, String newName, String path) {
    for (int i = 0; i < notes.size(); i++) {
      Note note = mapper.convertValue(notes.get(i), Note.class);
      if (matchesNote(note, oldName, path)) {
        note.setName(newName);
        note.setUpdatedAt(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        notes.set(i, mapper.convertValue(note, ObjectNode.class));
        break;
      }
    }
    writeChangesToIndex();
  }

  public void updateTime(String name, String path) {
    for (int i = 0; i < notes.size(); i++) {
      Note note = mapper.convertValue(notes.get(i), Note.class);
      if (matchesNote(note, name, path)) {
        note.setUpdatedAt(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        notes.set(i, mapper.convertValue(note, ObjectNode.class));
        break;
      }
    }
    writeChangesToIndex();
  }

  public void addTag(String tag) {
    boolean tagExists = false;
    for (int i = 0; i < tags.size(); i++) {
      if (tags.get(i).asText().equals(tag)) {
        tagExists = true;
        break;
      }
    }
    if (!tagExists) {
      tags.add(tag);
      writeChangesToIndex();
    }
  }

  public void removeTag(String tag) {
    for (int i = 0; i < notes.size(); i++) {
      Note note = mapper.convertValue(notes.get(i), Note.class);
      ArrayList<String> noteTags = note.getTags();
      if (noteTags != null && noteTags.contains(tag)) {
        noteTags.remove(tag);
        note.setTags(noteTags);
        notes.set(i, mapper.convertValue(note, ObjectNode.class));
      }
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
      Note note = mapper.convertValue(notes.get(i), Note.class);
      if (matchesNote(note, name, path)) {
        ArrayList<String> noteTags = note.getTags();
        if (noteTags == null) {
          noteTags = new ArrayList<>();
        }
        noteTags.add(tag);
        note.setTags(noteTags);
        notes.set(i, mapper.convertValue(note, ObjectNode.class));
      }
    }
    writeChangesToIndex();
  }

  public void removeTagFromNote(String name, String path, String tag) {
    for (int i = 0; i < notes.size(); i++) {
      Note note = mapper.convertValue(notes.get(i), Note.class);
      if (matchesNote(note, name, path)) {
        ArrayList<String> noteTags = note.getTags();
        if (noteTags != null && noteTags.contains(tag)) {
          noteTags.remove(tag);
          note.setTags(noteTags);
          notes.set(i, mapper.convertValue(note, ObjectNode.class));
        }
      }
    }
    writeChangesToIndex();
  }

  public ArrayList<String> getTags(String name, String path) {
    ArrayList<String> noteTags = new ArrayList<>();
    for (int i = 0; i < notes.size(); i++) {
      Note note = mapper.convertValue(notes.get(i), Note.class);
      if (matchesNote(note, name, path)) {
        noteTags = note.getTags();
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

  public ArrayList<Note> filterBasedOnTags(ArrayList<Note> searchedNotes, ArrayList<String> tags) {
    if (tags == null) return searchedNotes;
    ArrayList<Note> filteredNotes = new ArrayList<>();
    for (Note note : searchedNotes) {
      ArrayList<String> noteTags = note.getTags();
      if (noteTags != null && noteTags.containsAll(tags)) {
        filteredNotes.add(note);
      }
    }
    return filteredNotes;
  }
}
