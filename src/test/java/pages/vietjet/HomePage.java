package pages.vietjet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.HighlightOptions;
import com.codeborne.selenide.SelenideElement;
import common.LocaleManager;
import common.Log;
import common.Utilities;
import dataloader.FlightBookingData;
import dataobjects.BookingInformation;
import dataobjects.Passenger;
import element.Elements;
import enums.FlightDateTypes;
import enums.PassengerTypes;
import io.qameta.allure.Step;


import java.util.Map;
import java.util.ResourceBundle;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.switchTo;

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
    public HomePage(){
        super();
        logger.info("Initializing HomePage...");
    }

    @Step("Selecting {flightType} flight type")
    public void selectFlightType(String flightType){
        SelenideElement radFlightType = $x(String.format(this.radFlightType, flightType));
        if(!radFlightType.isSelected()){
            radFlightType.click();
        }
    }

    @Step("Input Depart Destination as {departDestination}")
    public void inputDepartDestination(String departDestination){
        SelenideElement inputDepartDestination = $x(txtDepartDestinationInput);
        inputDepartDestination.click();
        inputDepartDestination.setValue(departDestination);
        Elements.clickFormattedElement(optDestination, departDestination);
    }

    @Step("Input Arrival Destination as {arrivalDestination}")
    public void inputArrivalDestination(String arrivalDestination){
        SelenideElement inputArrivalDestination = $x(txtArrivalDestinationInput);
        inputArrivalDestination.click();
        inputArrivalDestination.setValue(arrivalDestination);
        Elements.clickFormattedElement(optDestination, arrivalDestination);
    }

    @Step("Selecting {flightDateType} date as {dateTime}")
    public void selectFlightDate(String dateTime, FlightDateTypes flightDateType){
        String[] splitDateTime = dateTime.split(",");
        String month = splitDateTime[0];
        String year = splitDateTime[1];
        String date = splitDateTime[2];

        if (!$x(tblCalendar).shouldBe(visible).isDisplayed()){
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

    public void inputPassengerQuantityByType(PassengerTypes passengerTypes, int quantity){
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

    @Step("Input passenger quantity for {types}")
    public void inputPassengerQuantity(Passenger types){
        //Convert the Passenger object to Map
        Map<String, Integer> passengerMap = types.toMap();

        //Iterate through the Passenger object and input the quantity
        passengerMap.forEach((key, value) -> {
            PassengerTypes passengerType = PassengerTypes.fromDisplayName(key);
            logger.info("Inputting passenger quantity for {}", passengerType.getDisplayName());
            inputPassengerQuantityByType(passengerType, value);
        });
    }

    @Step("Click search flight button")
    private void clickSearchFlightButton(){
        Elements.clickFormattedElement(btnSearchFlight, localeBundle.getString("homepage.booking.search"));
    }

    /**
     * Search for a flight base on the given information such as Depart, Arrival, Flight Date, Passengers
     * @param bookingInformation as information need to be searched on HomePage form
     * @throws IllegalArgumentException if missing information or SelenideElement is unable to find
     */
    @Step("Search for a flight with {bookingInformation}")
    public void searchFlight(BookingInformation bookingInformation){
        //There is a cookie popup appear and accept it
        acceptCookie();

        //Close the notification banner if it is displayed
        closeNotificationBanner();

        //Select the flight type
        selectFlightType(bookingInformation.flightType());

        //Input the Depart Destination as Airport Code e.g. SGN
        inputDepartDestination(bookingInformation.departDestination());

        //Input the Arrival Destination as Airport Code e.g. HAN
        inputArrivalDestination(bookingInformation.arrivalDestination());

        //Picking the departure and arrival day as counting number days from current day
        selectFlightDate(bookingInformation.departDate(), FlightDateTypes.DEPART_DATE);
        selectFlightDate(bookingInformation.arrivalDate(), FlightDateTypes.RETURN_DATE);

        //Input the quantity of passenger
        inputPassengerQuantity(bookingInformation.passenger());

        //Click search button
        clickSearchFlightButton();
    }


}
