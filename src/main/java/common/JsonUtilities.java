package common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class JsonUtilities {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T readJsonData(String filePath, TypeReference<T> typeReference){
        try (InputStream inputStream = Objects.requireNonNull(JsonUtilities.class.getClassLoader().getResourceAsStream(filePath),
                "[JSON UTIL] Unable to find Json File from path" + filePath)) {

            return mapper.readValue(inputStream, typeReference);

        } catch (IOException exception){
            throw new RuntimeException("[JSON UTIL] Error happens when reading Json file from: " +
                    filePath + " - " + exception.getMessage());
        }
    }
}
