package testcases.vietjet;

import com.codeborne.selenide.Selenide;
import dataloader.FlightBookingData;
import dataobjects.BookingInformation;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import listener.RetryAnalyzer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.vietjet.HomePage;
import pages.vietjet.PassengerInfoPage;
import pages.vietjet.TravelOptionPage;
import testcases.TestBase;

import java.util.Iterator;

public class BookingTicket extends TestBase {
    HomePage homePage = new HomePage();
    TravelOptionPage travelOptionPage = new TravelOptionPage();
    PassengerInfoPage passengerInfoPage = new PassengerInfoPage();

    @Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "data_TC_01")
    @Description("Search and choose tickets on a specific day successfully")
    @Owner("An Duong - 48288")
    public void TC_01(BookingInformation bookingInformation) {
        //Search For a ticket
        homePage.searchFlight(bookingInformation);
        //Choose ticket
        travelOptionPage.selectTicketsForFlight(bookingInformation);

        Selenide.sleep(3000);
        //Verify the Passenger Info Form is displayed
        passengerInfoPage.verifyPassengerInfoFormIsDisplayed();
    }

    @DataProvider(name = "data_TC_01")
    public Iterator<Object[]> data_TC_01() {
        return new FlightBookingData("TC_01").provide()
                .map(bookingInformation -> new Object[]{bookingInformation})
                .toList().iterator();
    }

}