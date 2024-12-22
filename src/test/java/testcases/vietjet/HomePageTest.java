package testcases.vietjet;

import listener.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

public class HomePageTest extends TestBase {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void test(){
        System.out.println("Executing testWithoutITestResultParameter");
        Assert.fail("This test will fail and be retried.");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void test2(){
        System.out.println("Test 2");
        Assert.assertTrue(true);
    }
}
