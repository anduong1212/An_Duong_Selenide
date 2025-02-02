package testcases.vietjet;

import com.codeborne.selenide.Selenide;
import dataobjects.BookingInformation;
import dataobjects.Passenger;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import listener.RetryAnalyzer;
import org.testng.annotations.Test;
import pages.vietjet.HomePage;
import pages.vietjet.TravelOptionPage;
import testcases.TestBase;

public class HomePageTest extends TestBase {
    HomePage homePage = new HomePage();
    TravelOptionPage travelOptionPage = new TravelOptionPage();

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Description("Search and choose tickets on a specific day successfully")
    @Owner("An.Duong-48288")
    public void test2() {
        Passenger passenger = new Passenger(2,0,0);
        BookingInformation bookingInformation = new BookingInformation("SGN", "HAN", "NO", "NO", "return", passenger);

        //Search For a ticket
        homePage.searchFlight(bookingInformation);

        //Will be included on below method
        homePage.closePopupAds();

        //Picked the lowest prices
        travelOptionPage.selectLowestTicket();

        Selenide.sleep(5000);
    }

}