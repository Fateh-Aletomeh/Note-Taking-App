package ucl.ac.uk.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Model {
  // Reads the json file and returns a map of id to name
  public HashMap<Integer, String> readJson() {
    HashMap<Integer, String> notesMap = new HashMap<>();

    try {
      File jsonFile = new File("data/notes.json");
      ObjectMapper objectMapper = new ObjectMapper();

      Map<String, Object>[] notesArray = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>[]>(){});
      for (Map<String, Object> note : notesArray) {
        System.out.println(note);
        Integer id = (Integer) note.get("id");
        String name = (String) note.get("name");
        notesMap.put(id, name);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return notesMap;
  }
}
