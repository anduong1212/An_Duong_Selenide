package common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonUtilities {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static Map<String, JsonNode> loadJsonObjectFromFile(String filePath){
        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
            return  mapper.convertValue(rootNode, Map.class);
        } catch (IOException e){
            throw new RuntimeException("Failed to load data from " + filePath);
        }
    }
}
