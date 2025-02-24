package leapfrog.dataloader;

import common.CSVUtilities;
import common.Utilities;
import leapfrog.dataobjects.Product;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

public class ProductsData {
    private final String  CSV_FILE_PATH = "src/test/resources/data/leapfrog_games.csv";

    //Declare the mapping function
    Function<String[], Product> productMapperFunction = line -> {
        if (line.length == 3){
            try {
                String title = line[0];
                String age = line[1];
                String price = line[2].replace(" ","");

                return new Product(title, age, price);
            } catch (NumberFormatException nfe){
                throw new RuntimeException("Error parsing CSV row to Product object: " + String.join(",", line), nfe);
            }
        } else {
            throw new RuntimeException("Invalid CSV row: " + String.join(",", line));
        }
    };

    @Getter
    List<Product> products = CSVUtilities.readCSV(CSV_FILE_PATH, productMapperFunction);

    public Product getProductByTitle(String title){
        return products.stream().filter(product -> product.title().equals(title)).findFirst().orElse(null);
    }

    public static void main(String[] args) {
        ProductsData productsData = new ProductsData();
        productsData.products.forEach(System.out::println);
    }


}
