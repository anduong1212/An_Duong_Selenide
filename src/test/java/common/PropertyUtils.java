package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class PropertyUtils {
    private static Properties properties;
    private static FileInputStream inputStream;
    private static FileOutputStream outputStream;
    private static final String defaultFilePath = "/src/test/resources/application.properties";
    private static volatile PropertyUtils instance;

    private PropertyUtils(){
        loadProperties();
    }

    private void loadProperties(){
        properties = new Properties();
        try{
            inputStream = new FileInputStream(Utilities.getProjectPath() + defaultFilePath);
            //Load properties
            properties.load(inputStream);

        } catch (IOException exception){
            Log.error(exception.getMessage());
        }
    }

    public void loadPropertiesFromFile(String filePath) {
        Properties properties1 = new Properties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(Utilities.getProjectPath() + filePath);
            properties1.load(fileInputStream);
            properties.putAll(properties1);

        }catch (IOException e){
            Log.error(e.getMessage());
        }
    }
    public String getPropertyValue(String keyOfProp){
        return getProperties().getProperty(keyOfProp);
    }

    public Properties getProperties(){
        return properties;
    }

    public static synchronized PropertyUtils getInstance() {
        if (properties == null) instance = new PropertyUtils();
        return instance;
    }

    public static void setPropertiesToFile(Map<String, String> propsMap, String outputFilePath){
        File file = new File(outputFilePath);
        Path path = Paths.get(outputFilePath);

        try (FileOutputStream output = new FileOutputStream(outputFilePath)){
            Properties props = new Properties();
            for (String key : propsMap.keySet()){
                props.setProperty(key, propsMap.get(key));
            }
            if (file.exists()){
                props.store(output, null);
            } else {
                Files.createFile(path);
                props.store(output, null);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
