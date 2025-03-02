package leapfrog.testcases;

import leapfrog.dataloader.TabConfigData;
import leapfrog.dataobjects.TabResult;
import leapfrog.dataobjects.TabConfig;
import leapfrog.threadprocessing.LeapFrogTabProcessingTask;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.testng.Assert.fail;

public class ProductValidation extends TestBase {

    private final List<TabConfig> configs = new TabConfigData().provide().findFirst().get();
    private final int MAX_THREADS = 3;
    private final org.testng.log4testng.Logger logger = Logger.getLogger(ProductValidation.class);

    @Test
    public void validateProduct() {
        try {
            if (configs.isEmpty()) {
                fail("No Tab Configurations loaded from JSON file. Check leapfrog-tab-config.json.");
            }

            ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
            //Define a list to store the futures
            List<Future<TabResult>> futures = new ArrayList<>();

            //Iterate through the tab configurations, create a task for each tab and submit it to the executor
            for (TabConfig config : configs) {
                Callable<TabResult> tabTask = new LeapFrogTabProcessingTask(config);

                //Submit the task to the executor
                futures.add(executorService.submit(tabTask));
            }

            executorService.shutdown();
            executorService.awaitTermination(10, java.util.concurrent.TimeUnit.MINUTES);

            //Iterate through the futures and get the results
            List<TabResult> tabResults = new ArrayList<>();
            for (Future<TabResult> future : futures) {
                //Get the result from the future
                tabResults.add(future.get());
            }

            //Iterate through the results and log the status
            for (TabResult tabResult : tabResults) {
                if (!tabResult.success()) {
                    Assert.fail("Validation failed: " + tabResult.errors());
                } else {
                    logger.info("Validation passed for tab: " + tabResult.tabName());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Error waiting for executor service to terminate", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Error executing task", e);
        }
    }

}
