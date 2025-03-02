package common;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
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

    /**
     * Write list objects to CSV file
     *
     * @param objects     List of objects need to write to CSV.
     * @param csvFilePath Output CSV file path.
     * @param header      Header of CSV file.
     * @param dataMapper  Function to map object to String array which represent for columns in CSV.
     * @param <T>         Type of object in the list.
     */
    public static <T> void writeObjectsToCsv(List<T> objects, String csvFilePath, String[] header, Function<T, String[]> dataMapper) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            writer.writeNext(header);

            for (T obj : objects) {
                String[] data = dataMapper.apply(obj); //
                if (data != null) {
                    writer.writeNext(data);
                } else {
                    System.err.println("Warning: Data mapper returned null for object: " + obj + ". Skipping row.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error during writing the CSV: " + csvFilePath);
        }
    }
}

