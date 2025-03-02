package leapfrog.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import common.CSVUtilities;
import leapfrog.dataloader.ProductsData;
import leapfrog.dataobjects.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ProductPage {
    /**
     * Get product list from a page
     * @param pageNumber page number
     * @return List of products
     */
    public List<Product> getProductListFromPage(int pageNumber) {
        String pageSources = WebDriverRunner.driver().source();
        ProductPage page = new ProductPage();

        String parentSelector = "h2#results__heading+div div.catalog-product";
        Document doc = Jsoup.parse(pageSources);
        Elements parentElement = doc.select(parentSelector);

        //Data get from HTML DOM
        List<Product> productsFromWeb = new ArrayList<>();

        for (Element element : parentElement) {
            String productName = element.select("p a").text();
            String productAge = element.select("p.ageDisplay").text().replace("Ages ", "");
            String productPrice = element.selectFirst("p.prices span").text().replace("Price: ", "");
            productsFromWeb.add(new Product(pageNumber, productName, productAge, productPrice));
        }
        return productsFromWeb;
    }

    public boolean compareProductLists(List<Product> list1, List<Product> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }

        if (list1 == null || list2 == null || list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            Product product1 = list1.get(i);
            System.out.println("Product expected: " + product1.toString());
            Product product2 = list2.get(i);
            System.out.println("Product actual: " + product2.toString());
            if (!product1.equals(product2)) {
                return false;
            }
        }
        return true;
    }


}
