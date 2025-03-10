package agoda.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import common.Utilities;
import element.control.DatePicker;
import element.control.factories.DatePickerFactories;
import element.control.factories.DatePickerSelector;
import io.qameta.allure.Step;
import org.testng.log4testng.Logger;

import java.time.Duration;
import java.util.function.Supplier;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class BookingPage extends BasePage{
    private final String optDestinationOption = "ul li[data-selenium=autosuggest-item][data-element-value='%s']";
    private final String btnCalendar = "div[data-element-name=%s]";
    private final String tbcCalendarDay = "span[data-selenium-date='%s']";
    private final String txtQuantity = "div[data-selenium=desktop-occ-%s-value]";
    private final String increaseQuantity = "button[data-selenium=plus][data-element-name=occupancy-selector-panel-%s]";
    private final Supplier<SelenideElement> txtCalendarParent = () -> $("div.DayPicker-Month-Wide:nth-child(1)");
    private final Supplier<SelenideElement> inputDestination = () -> $("div[data-selenium=icon-box-child] input[aria-label='Enter a destination or property']");
    private final Supplier<SelenideElement> txtCalendarMonthYear = () -> $("div.DayPicker-Month-Wide");
    private final Supplier<SelenideElement> btnOccupancyBox = () -> $("div[data-selenium=occupancyBox]");
    private final Logger logger = Logger.getLogger(BookingPage.class);

    public BookingPage(){
        super();
        logger.info("Initializing Booking Page...");
    }

    @Step("Selecting destination: {destination}")
    public void selectDestination(String destination){
        inputDestination.get().setValue(destination);
        SelenideElement destinationElement = $(String.format(optDestinationOption, destination)).shouldBe(visible);
        destinationElement.click();
    }

    @Step("Launching calendar for {typeOfDate}")
    private void launchCalendar(String typeOfDate){
        if(!txtCalendarMonthYear.get().shouldBe(visible).isDisplayed()){
            SelenideElement calendarButton = $(String.format(btnCalendar, typeOfDate));
            calendarButton.click();
            logger.info("Opened calendar for " + typeOfDate);
        } else {
            logger.info("Calendar is already open");
        }
    }

    @Step("Launching occupancy box")
    private void launchOccupancyBox(){
        if(!btnOccupancyBox.get().shouldBe(visible).isDisplayed()){
            btnOccupancyBox.get().click();
            logger.info("Opened occupancy box");
        } else {
            logger.info("Occupancy box is already open");
        }
    }

    @Step("Selecting date: {date}")
    private void selectDate(String date){
        logger.info("Selecting date: " + date);
        SelenideElement dayElement = $(String.format(tbcCalendarDay, date)).shouldBe(visible, Duration.ofSeconds(5));
        dayElement.click();
    }

    @Step("Selecting check-in date: {checkInDate} and check-out date: {checkOutDate}")
    public void selectBookingDate(String checkInDate, String checkOutDate){
        launchCalendar("check-in-box");
        selectDate(checkInDate);
        selectDate(checkOutDate);
    }

    @Step("Selecting quantity for {typeOfControl}")
    public void inputBookingQuantity(String typeOfControl, int quantity){
        launchOccupancyBox();

        Supplier<SelenideElement> formattedQuantityLabel = () -> $(String.format(txtQuantity, typeOfControl)).shouldBe(visible, Duration.ofSeconds(5));

        int currentQuantity = Integer.parseInt(formattedQuantityLabel.get().getText());
        while(currentQuantity != quantity){
            SelenideElement increaseQuantityButton = $(String.format(increaseQuantity, typeOfControl));
            increaseQuantityButton.click();
            currentQuantity = Integer.parseInt(formattedQuantityLabel.get().getText());
        }
    }


    public static void main(String[] args) {
        Selenide.open("https://www.agoda.com/");
        BookingPage bookingPage = new BookingPage();
        bookingPage.selectDestination("Da Nang");
        bookingPage.selectBookingDate("2025-03-16", "2025-03-18");
        bookingPage.inputBookingQuantity("adult", 10);
        Selenide.sleep(3000);
        Selenide.closeWebDriver();
    }
}
