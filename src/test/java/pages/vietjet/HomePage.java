package pages.vietjet;

import com.codeborne.selenide.SelenideElement;
import common.LocaleManager;
import dataobjects.BookingInformation;
import dataobjects.Passenger;
import element.Elements;
import element.control.DatePicker;
import element.control.factories.DatePickerFactories;
import enums.vietjet.FlightDateTypes;
import enums.vietjet.PassengerTypes;
import io.qameta.allure.Step;


import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class HomePage extends BasePage {
    private final String txtDepartDestinationInput = "//input[@class='MuiInputBase-input MuiOutlinedInput-input' and not(@id)]";
    private final String txtArrivalDestinationInput = "//input[@class='MuiInputBase-input MuiOutlinedInput-input' and @id]";
    private final String btnAcceptCookie = "//div[@id='popup-dialog-description']/following-sibling::div/button";
    private final String tltCookiePopupTitle = "//div[@id='popup-dialog-description']//span[text()='%s']";
    private final String optDestination = "//div[@id='panel1a-content']//div[text()='%s']";
    private final String radFlightType = "//img[@src='/static/media/switch.d8860013.svg']/parent::div/preceding-sibling::div//input[@type='radio'and@value='%s']";
    private final String tblCalendar = "//div[@class='rdrCalendarWrapper rdrDateRangeWrapper']";
    private final String btnPassenger = "//div//input[contains(@id,'input-base-custom')]";
    private final String lblPassengerType = "//div/p[text()='%s']";
    private final String lblPassengerQuantity = "//div//p[text()='%s']//ancestor::div/following-sibling::div/span[contains(@class, 'MuiTypography-root')]";
    private final String btnDecreasePassengerQuantity =  "/preceding-sibling::button";
    private final String btnIncreasePassengerQuantity = "/following-sibling::button";
    private final String btnSearchFlight = "//div/button[contains(@class,'jss')]//span[text()=\"Let's go\"]";

    private final String btnPickingDateCalendar = "//div//p[text()='%s']";
    private final String btnDateOnCalendar = "//div[@class='rdrMonth' and contains(div,'%s')]//span[text()='%s']";

    private final DatePicker picker;


    private final ResourceBundle localeBundle = LocaleManager.getLocaleBundle("homepage");
    public HomePage(){
        super();
        logger.info("Initializing HomePage...");
        picker = DatePickerFactories.createDatePicker($x(tblCalendar), "VietJet");
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
    public void selectFlightDate(BookingInformation bookingInformation){

        String departDate = bookingInformation.departDate();
        String arrivalDate = bookingInformation.arrivalDate();
        String flightType = bookingInformation.flightType();

        logger.info("Selecting tickets for {} flight.", flightType);
        if ("roundTrip".equalsIgnoreCase(flightType)) {
            logger.info("Selecting tickets for both departure and arrival for return flight.");
            picker.selectDate(departDate, FlightDateTypes.DEPART_DATE.getFlightDateType()); // Indicate departure flight
            picker.selectDate(arrivalDate, FlightDateTypes.RETURN_DATE.getFlightDateType());   // Indicate arrival flight
        } else if ("oneway".equalsIgnoreCase(flightType)) {
            logger.info("Selecting ticket for oneway flight.");
            picker.selectDate(departDate, FlightDateTypes.DEPART_DATE.getFlightDateType());      // Indicate oneway flight
        } else {
            logger.warn("Unknown flight type: {}. Selecting ticket as if it's oneway.", flightType);
            picker.selectDate(departDate, FlightDateTypes.DEPART_DATE.getFlightDateType()); // Handle unknown type gracefully
        }
    }

    @Step("Accept Cookie popup")
    public void acceptCookie(){
        if(Elements.isFormattedElementDisplayed(tltCookiePopupTitle, localeBundle.getString("homepage.popup.title.cookie"))){
            $x(btnAcceptCookie).click();
        }
    }

    public void inputPassengerQuantityByType(PassengerTypes passengerTypes, int quantity){
        String formattedLblPassengerQuantity = String.format(lblPassengerQuantity, passengerTypes.getDisplayName());
        Supplier<SelenideElement> passengerQuantity = () -> $x(formattedLblPassengerQuantity);
        if (!Elements.isFormattedElementDisplayed(lblPassengerType, passengerTypes.getDisplayName())){
            $x(btnPassenger).click();
        }
        int currentPassengerValue = Integer.parseInt(passengerQuantity.get().getText());
        while (currentPassengerValue != quantity){
            Elements.clickFormattedElement(formattedLblPassengerQuantity + btnIncreasePassengerQuantity, passengerTypes.getDisplayName());
            currentPassengerValue = Integer.parseInt(passengerQuantity.get().getText());
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
        selectFlightDate(bookingInformation);

        //Input the quantity of passenger
        inputPassengerQuantity(bookingInformation.passenger());

        //Click search button
        clickSearchFlightButton();
    }


}
