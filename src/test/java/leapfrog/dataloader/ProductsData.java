package leapfrog.dataloader;

import com.fasterxml.jackson.core.type.TypeReference;
import common.CSVUtilities;
import common.JsonUtilities;
import common.Utilities;
import leapfrog.dataobjects.Product;
import leapfrog.dataobjects.TabConfig;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

public class ProductsData {
    private final String  CSV_FILE_PATH = "src/test/resources/data/leapfrog/leapfrog_games.csv";

    //Declare the mapping function
    public Function<String[], Product> productMapperFunction = line -> {
        if (line.length == 4){
            try {
                int page = Integer.parseInt(line[0]);
                String title = line[1];
                String age = line[2];
                String price = line[3].replace(" ","");

                return new Product(page, title, age, price);
            } catch (NumberFormatException nfe){
                throw new RuntimeException("Error parsing CSV row to Product object: " + String.join(",", line), nfe);
            }
        } else {
            throw new RuntimeException("Invalid CSV row: " + String.join(",", line));
        }
    };

    public Function<Product, String[]> productToCSVMapperFunction = product ->
            new String[]{String.valueOf(product.page()), product.title(), product.age(), product.price()};

    @Getter
    List<Product> products = CSVUtilities.readCSV(CSV_FILE_PATH, productMapperFunction);

    public Product getProductByTitle(String title){
        return products.stream().filter(product -> product.title().equals(title)).findFirst().orElse(null);
    }





}
