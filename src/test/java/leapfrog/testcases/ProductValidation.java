package leapfrog.testcases;

import leapfrog.dataloader.ProductsData;
import leapfrog.dataloader.TabConfigData;
import leapfrog.dataloader.TabResult;
import leapfrog.dataobjects.Product;
import leapfrog.dataobjects.TabConfig;
import leapfrog.pages.ProductPage;
import org.openqa.selenium.concurrent.ExecutorServices;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.testng.Assert.fail;

public class ProductValidation extends TestBase {

    private final List<TabConfig> configs = new TabConfigData().provide().findFirst().get();
    private final int MAX_THREADS = 3;

    @Test
    public void validateProduct() {

            if (configs.isEmpty()) {
                fail("No Tab Configurations loaded from JSON file. Check leapfrog-tab-config.json.");
            }

            ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
            //Define a list to store the futures
            List<Future<TabResult>> futures = new ArrayList<>();

            for (TabConfig config : configs) {
                Callable<TabResult> tabTask = new
            }






    }


}
