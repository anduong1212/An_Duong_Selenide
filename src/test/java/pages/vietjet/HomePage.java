package pages.vietjet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import common.LocaleManager;
import enums.FlightDateTypes;


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
    private final String btnPassenger = "//input[@id='input-base-custom-107']";

    public void selectFlightType(String flightType){
        $x(String.format(radFlightType, flightType)).click();
    }
    public void inputDepartDestination(String departDestination){
        SelenideElement inputDepartDestination = $x(txtDepartDestinationInput);
        inputDepartDestination.click();
        inputDepartDestination.setValue(departDestination);
        $x(String.format(optDestination, departDestination)).shouldBe(Condition.visible).click();
    }

    public void inputArrivalDestination(String arrivalDestination){
        SelenideElement inputArrivalDestination = $x(txtArrivalDestinationInput);
        inputArrivalDestination.click();
        inputArrivalDestination.setValue(arrivalDestination);
        $x(String.format(optDestination, arrivalDestination)).shouldBe(Condition.visible).click();
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

        $x(String.format(btnDateOnCalendar,month + " " + year, date)).click();
    }

    public void acceptCookie(){
        if($x(String.format(tltCookiePopupTitle,LocaleManager.getLocalizedText("homepage.popup.title.cookie")))
                .shouldBe(Condition.visible)
                .isDisplayed()){
            $x(btnAcceptCookie).click();
        }

    }

}
