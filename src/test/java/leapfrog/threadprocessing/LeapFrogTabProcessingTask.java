package leapfrog.threadprocessing;

import com.codeborne.selenide.Selenide;
import leapfrog.dataloader.ProductsData;
import leapfrog.dataloader.TabConfigData;
import leapfrog.dataobjects.TabResult;
import leapfrog.dataobjects.Product;
import leapfrog.dataobjects.TabConfig;
import leapfrog.pages.ProductPage;

import java.util.List;
import java.util.concurrent.Callable;

public class LeapFrogTabProcessingTask implements Callable<TabResult> {
    private final TabConfig TAB_CONFIG;
    private final TabConfigData TAB_CONFIG_LOADER = new TabConfigData();
    private final org.testng.log4testng.Logger logger;
    private final ProductPage productPage = new ProductPage();

    public LeapFrogTabProcessingTask(TabConfig tabConfig) {
        this.TAB_CONFIG = tabConfig;
        this.logger = org.testng.log4testng.Logger.getLogger(LeapFrogTabProcessingTask.class);
    }

    @Override
    public TabResult call() {
        TabResult tabResult = new TabResult(TAB_CONFIG.tabName());

        for (int pageNumber = TAB_CONFIG.startPage(); pageNumber <= TAB_CONFIG.endPage(); pageNumber++) {
            //Process the page
            String pageURL = TAB_CONFIG_LOADER.getURLForPage(pageNumber);
            try {
                Selenide.open(pageURL);
                logger.info("Tab '" + TAB_CONFIG.tabName() + "', Page " + pageNumber + " opened, URL: " + pageURL + ", Thread: " + Thread.currentThread().getName());

                //Get the product list from the page
                List<Product> productList = productPage.getProductListFromPage(pageNumber);

                //Get the expected product list from the CSV file
                ProductsData EXPECTED_DATA_LOADER = new ProductsData(TAB_CONFIG.csvFilePath());
                List<Product> expectedProductList = EXPECTED_DATA_LOADER.getProductOnPage(pageNumber).toList();

                //Assert the product list
                if (!productPage.compareProductLists(productList, expectedProductList)) {
                    tabResult = tabResult.addError("Product list mismatch on page " + pageNumber);
                }

            } catch (Exception e) {
                tabResult = tabResult.addError("Error in tab " + TAB_CONFIG.tabName() + ", page " + pageNumber + ": " + e.getMessage());
                throw new RuntimeException("Error processing page " + pageNumber, e);
            } catch (AssertionError ae) {
                tabResult = tabResult.addError("Assertion error in tab " + TAB_CONFIG.tabName() + ", page " + pageNumber + ": " + ae.getMessage());
                throw new RuntimeException("Data not match " + pageNumber, ae);
            }
        }

        return tabResult;
    }
}
