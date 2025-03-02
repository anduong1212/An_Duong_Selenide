package leapfrog.pages;

import com.codeborne.selenide.WebDriverRunner;
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
    private final org.testng.log4testng.Logger logger4j = org.testng.log4testng.Logger.getLogger(ProductPage.class);

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
            logger4j.error("Both product lists are null");
            return true;
        }

        if (list1 == null || list2 == null || list1.size() != list2.size()) {
            logger4j.error("Product list size mismatch");
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            Product product1 = list1.get(i);
            Product product2 = list2.get(i);

            if (!product1.equals(product2)) {
                logger4j.info("[FAILED] Product expected: " + product1.toString());
                logger4j.info("[FAILED] Product actual: " + product2.toString());
                return false;
            }
        }
        return true;
    }


}
