package testcases.vietjet;

import com.codeborne.selenide.Selenide;
import common.LocaleManager;
import common.Utilities;
import enums.FlightDateTypes;
import enums.PassengerTypes;
import listener.RetryAnalyzer;
import org.testng.annotations.Test;
import pages.vietjet.HomePage;
import testcases.TestBase;

public class HomePageTest extends TestBase {
    HomePage homePage = new HomePage();
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void test2(){
        homePage.acceptCookie();
//        homePage.inputDepartDestination("SGN");
//        homePage.inputArrivalDestination("HAN");
//        homePage.selectFlightDate(Utilities.getDateInPrior(1), FlightDateTypes.DEPART_DATE);
//        homePage.selectFlightDate(Utilities.getDateInPrior(4), FlightDateTypes.RETURN_DATE);
        homePage.inputPassengerQuantity(PassengerTypes.ADULTS, 2);
       Selenide.sleep(1000);

    }

    public static void main(String[] args) {
        LocaleManager.loadLocale("EN");
        System.out.println(PassengerTypes.fromDisplayName("Adults").getDisplayName());

    }
}
