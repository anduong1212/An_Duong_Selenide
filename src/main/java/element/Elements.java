package element;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public class Elements {
    public static boolean isFormattedElementDisplayed(String xpath, String replaceText){
        return $x(String.format(xpath, replaceText)).shouldBe(Condition.visible).isDisplayed();
    }

    public static void clickFormattedElement(String xpath, String... replaceText){
        $x(String.format(xpath, replaceText)).shouldBe(Condition.visible).click();
    }

    public static void handleCalendar(String calendar, String calendarButton, String dateButton, String month, String year, String date){
    }

    //Example overnight, overday, oneway, roundTrip
    public static void handleBookingType(String bookingType, String bookingTypeButton){
    }

    //Handle the passenger quantity
    public static void handlePassengerQuantity(String passengerQuantity, String passengerQuantityButton, String passengerType){
    }
}
