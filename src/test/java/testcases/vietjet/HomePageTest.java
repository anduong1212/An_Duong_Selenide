package testcases.vietjet;

import dataloader.FlightBookingData;
import dataobjects.BookingInformation;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import listener.RetryAnalyzer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.vietjet.HomePage;
import pages.vietjet.TravelOptionPage;
import testcases.TestBase;

import java.util.Iterator;

public class HomePageTest extends TestBase {
    HomePage homePage = new HomePage();
    TravelOptionPage travelOptionPage = new TravelOptionPage();

    @Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "data_TC_01")
    @Description("Search and choose tickets on a specific day successfully")
    @Owner("An Duong - 48288")
    public void TC_01(BookingInformation bookingInformation) {
        //Search For a ticket
        homePage.searchFlight(bookingInformation);

        //Will be included on below method
        homePage.closePopupAds();
    }

    @DataProvider(name = "data_TC_01")
    public Iterator<Object[]> data_TC_01() {
        return new FlightBookingData("TC_01").provide()
                .map(bookingInformation -> new Object[]{bookingInformation})
                .toList().iterator();
    }

}