package pages.vietjet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import common.LocaleManager;
import common.Utilities;
import dataobjects.BookingInformation;
import element.Elements;
import enums.FlightDateTypes;
import enums.PassengerTypes;
import io.qameta.allure.Step;


import java.util.ResourceBundle;

import static com.codeborne.selenide.Selenide.$x;

public class HomePage extends BasePage {
    private final String txtDepartDestinationInput = "//input[@class='MuiInputBase-input MuiOutlinedInput-input' and not(@id)]";
    private final String txtArrivalDestinationInput = "//input[@class='MuiInputBase-input MuiOutlinedInput-input' and @id]";
    private final String btnAcceptCookie = "//div[@id='popup-dialog-description']/following-sibling::div/button";
    private final String tltCookiePopupTitle = "//div[@id='popup-dialog-description']//span[text()='%s']";
    private final String optDestination = "//div[@id='panel1a-content']//div[text()='%s']";
    private final String radFlightType = "//img[@src='/static/media/switch.d8860013.svg']/parent::div/preceding-sibling::div//input[@type='radio'and@value='%s']";
    private final String btnPickingDateCalendar = "//div//p[text()='%s']";
    private final String btnDateOnCalendar = "//div[@class='rdrMonth' and contains(div,'%s')]//span[text()='%s']";
    private final String tblCalendar = "//div[@class='rdrCalendarWrapper rdrDateRangeWrapper']";
    private final String btnPassenger = "//div//input[contains(@id,'input-base-custom')]";
    private final String lblPassengerType = "//div/p[text()='%s']";
    private final String lblPassengerQuantity = "//div//p[text()='%s']//ancestor::div/following-sibling::div/span[contains(@class, 'MuiTypography-root')]";
    private final String btnDecreasePassengerQuantity =  "/preceding-sibling::button";
    private final String btnIncreasePassengerQuantity = "/following-sibling::button";
    private final String btnSearchFlight = "//div/button[contains(@class,'jss')]//span[text()=\"Let's go\"]";

    private final ResourceBundle localeBundle = LocaleManager.getLocaleBundle("homepage");

    public void selectFlightType(String flightType){
        $x(String.format(radFlightType, flightType)).click();
    }
    public void inputDepartDestination(String departDestination){
        SelenideElement inputDepartDestination = $x(txtDepartDestinationInput);
        inputDepartDestination.click();
        inputDepartDestination.setValue(departDestination);
        Elements.clickFormattedElement(optDestination, departDestination);
    }

    public void inputArrivalDestination(String arrivalDestination){
        SelenideElement inputArrivalDestination = $x(txtArrivalDestinationInput);
        inputArrivalDestination.click();
        inputArrivalDestination.setValue(arrivalDestination);
        Elements.clickFormattedElement(optDestination, arrivalDestination);
    }

    public void selectFlightDate(String dateTime, FlightDateTypes flightDateType){
        String[] splitDateTime = dateTime.split(",");
        String month = splitDateTime[0];
        String year = splitDateTime[1];
        String date = splitDateTime[2];

        if (!$x(tblCalendar).shouldBe(Condition.visible).isDisplayed()){
            $x(String.format(btnPickingDateCalendar, flightDateType.getFlightDateType()))
                    .click();
        }
        Elements.clickFormattedElement(btnDateOnCalendar, month + " " + year, date);
    }

    @Step("Accept Cookie popup")
    public void acceptCookie(){
        if(Elements.isFormattedElementDisplayed(tltCookiePopupTitle, localeBundle.getString("homepage.popup.title.cookie"))){
            $x(btnAcceptCookie).click();
        }
    }

    public void inputPassengerQuantity(PassengerTypes passengerTypes, int quantity){
        String formattedLblPassengerQuantity = String.format(lblPassengerQuantity, passengerTypes.getDisplayName());

        if (!Elements.isFormattedElementDisplayed(lblPassengerType, passengerTypes.getDisplayName())){
            $x(btnPassenger).click();
        }

        int defaultPassenger = Integer.parseInt($x(formattedLblPassengerQuantity).getText());

        while (defaultPassenger < quantity){
            Elements.clickFormattedElement(formattedLblPassengerQuantity + btnIncreasePassengerQuantity, passengerTypes.getDisplayName());
            defaultPassenger++;
        }
    }

    /**
     * Search for a flight base on the given information such as Depart, Arrival, Flight Date, Passengers
     *
     * @param bookingInformation as information need to be searched on HomePage form
     * @throws IllegalArgumentException if missing information or SelenideElement is unable to find
     */
    public void searchFlight(BookingInformation bookingInformation){
        //Verify that there is a cookie popup appear and accept it
        acceptCookie();

        //Input the Depart Destination as Airport Code e.g. SGN
        inputDepartDestination(bookingInformation.departDestination());

        //Input the Arrival Destination as Airport Code e.g. HAN
        inputArrivalDestination(bookingInformation.arrivalDestination());

        //Picking the departure and arrival day as counting number days from current day
        selectFlightDate(Utilities.getDateInPrior(1), FlightDateTypes.DEPART_DATE);
        selectFlightDate(Utilities.getDateInPrior(4), FlightDateTypes.RETURN_DATE);

        //Input the quantity of passenger
        inputPassengerQuantity(PassengerTypes.ADULTS, bookingInformation.passenger().adults());

        //Click search button
        Elements.clickFormattedElement(btnSearchFlight, localeBundle.getString("homepage.booking.search"));
    }

}
