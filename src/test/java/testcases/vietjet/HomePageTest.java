package testcases.vietjet;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import common.PropertyUtils;
import listener.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.vietjet.HomePage;
import testcases.TestBase;

public class HomePageTest extends TestBase {
    HomePage homePage = new HomePage();
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void test2(){

        homePage.acceptCookie();
        homePage.inputDepartDestination("SGN");
        homePage.inputArrivalDestination("HAN");
        homePage.selectDepartureDate();
       Selenide.sleep(1000);

    }
}
