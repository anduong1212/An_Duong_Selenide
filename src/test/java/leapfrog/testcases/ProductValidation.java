package leapfrog.testcases;

import leapfrog.dataloader.ProductsData;
import leapfrog.dataobjects.Product;
import leapfrog.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class ProductValidation extends TestBase {

    ProductPage productPage = new ProductPage();
    ProductsData data = new ProductsData();

    public void validateProduct() {
        List<Product> actualList = productPage.getProductListFromPage();
        boolean result = productPage.compareProductLists(actualList, data.getProducts());
        Assert.assertTrue(result);
    }


}
