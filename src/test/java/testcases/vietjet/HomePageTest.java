package testcases.vietjet;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import listener.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestBase;

public class HomePageTest extends TestBase {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void test2(){
        System.out.println("Test 2");
        Selenide.open(Configuration.baseUrl);
        Assert.assertTrue(true);
    }
}
