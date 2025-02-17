package element.control;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import common.LocaleManager;
import element.Elements;
import element.control.factories.DatePickerSelector;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class DatePicker {
    //This is the root element of the date picker
    private final DatePickerSelector selector;
    private final Supplier<SelenideElement> datePickerRoot;
    private final Logger logger = Logger.getLogger(DatePicker.class.getName());

    //This is the date picker element
    private final String calendarButton;
    private final Supplier<SelenideElement> calendarMonthYear;
    private final Supplier<SelenideElement> nextMonthButton;
    private final Supplier<SelenideElement> previousMonthButton;
    private final String dayCell;

    public DatePicker(SelenideElement rootElement, DatePickerSelector selector){
        this.selector = selector;
        this.datePickerRoot = () -> rootElement;

        this.calendarButton = this.selector.calendarButtonSelector();
        this.calendarMonthYear = () -> datePickerRoot.get().$(this.selector.calendarMonthYearSelector());
        this.nextMonthButton = () -> datePickerRoot.get().$(this.selector.nextMonthButtonSelector());
        this.previousMonthButton = () -> datePickerRoot.get().$(this.selector.previousMonthButtonSelector());
        this.dayCell = this.selector.dayCellXPathFormat();
    }

    private Boolean isCalendarPopupVisible(){
        return datePickerRoot.get().shouldBe(visible).isDisplayed();
    }

    private void openCalendarPopup(String calendarType){
        if(!isCalendarPopupVisible()){
            Elements.clickFormattedElement(calendarButton, calendarType);
            logger.info("[Date Picker] Opened calendar popup");
        } else {
            logger.info("[Date Picker] Calendar popup is already open");
        }
    }

    /**
     * Select a date on the date picker
     * @param dateTime the date to select
     * @param calendarType the type of date picker to select
     */
    public void selectDate(String dateTime, String calendarType) {
        openCalendarPopup(calendarType);

        //Handle the date selection
        String[] splitDateTime = dateTime.split(",");
        String month = splitDateTime[0];
        String year = splitDateTime[1];
        String date = splitDateTime[2];

        String targetMonthYear = month + " " + year;
        String currentMonthYear = calendarMonthYear.get().shouldBe(Condition.appear).getText();

        logger.info("[Date Picker] currentMonthYear: " + currentMonthYear);

        String dateFormat = LocaleManager.getSelectedLocale().getLocaleDateFormat();
        Locale selectedLocale = LocaleManager.getSelectedLocale().getLocale();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat, selectedLocale);
        LocalDate targetDate = LocalDate.parse(dateTime, dateFormatter);

        while (!currentMonthYear.equals(targetMonthYear)) {
            if (targetDate.isAfter(LocalDate.parse(currentMonthYear.replace(" ", ",") + ",1", dateFormatter))) {
                nextMonthButton.get().click();
            } else {
                previousMonthButton.get().click();
            }
            currentMonthYear = calendarMonthYear.get().shouldNot(Condition.text(currentMonthYear), Duration.ofMillis(Configuration.timeout)).getText();
        }

        //Select the date
        logger.info("[Date Picker] Selecting date " + targetMonthYear + "," + date);
        Elements.clickFormattedElement(dayCell, currentMonthYear, date);
    }
}
