package common;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CSVUtilities {
    /**
     * Read CSV file and map each row to an object
     * @param filePath Path to the CSV file
     * @param mapper Function to map the CSV row (String[]) to the T object
     * @return List of T objects
     * @param <T>
     */
    public static <T> List<T> readCSV(String filePath, Function<String[], T> mapper){
        List<T> dataList = new ArrayList<>();
        String encode = System.getProperty("csv.encoder", "UTF-8");

        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), encode))) {
            //Read the header of CSV file if available
            String[] header = reader.readNext();

            String[] lineInArray;
            while((lineInArray = reader.readNext()) != null){
               try {
                   //Mapping the line to the record
                   T record = mapper.apply(lineInArray);

                   if(record != null){
                       dataList.add(record);
                   }
               } catch (Exception e){
                   throw new RuntimeException("Error mapping CSV row to object: " + String.join(",", lineInArray), e);
               }
            }
        } catch (IOException ioe){
            throw new RuntimeException("Error reading CSV file: " + filePath, ioe);
        } catch (CsvValidationException csve){
            throw new RuntimeException("CSV validating CSV file: " + filePath, csve);
        }
        return dataList;
    }
}
