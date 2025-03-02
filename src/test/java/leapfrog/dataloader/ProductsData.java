package leapfrog.dataloader;

import com.fasterxml.jackson.core.type.TypeReference;
import common.CSVUtilities;
import common.JsonUtilities;
import common.Utilities;
import dataprovider.DataProvider;
import leapfrog.dataobjects.Product;
import leapfrog.dataobjects.TabConfig;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductsData {

    private String FILE_PATH;
    public ProductsData(String filePath){
        this.FILE_PATH = filePath;
    }
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


    public Stream<Product> getProductOnPage(int pageNumber) {
        {
            List<Product> products = CSVUtilities.readCSV(FILE_PATH, productMapperFunction);

            if (products == null || products.isEmpty()) {
                System.err.println("Warning: No product data loaded from CSV file: " + FILE_PATH);
                return Stream.empty();
            }

            return products.stream()
                    .filter(product -> product.page() == pageNumber)
                    .toList() // Collect to List first for easier debugging/logging (optional)
                    .stream();
        }
    }
}
