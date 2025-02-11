package pages.vietjet;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.function.Supplier;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class PassengerInfoPage extends BasePage{
    private final Supplier<SelenideElement> frmPassengerInfoForm = () -> $x("//i[@class = 'fa fa-male']/ancestor::div[contains(@style,'padding-bottom')]");

    public PassengerInfoPage(){
        super();
        logger.info("Initializing Passenger Info Page...");
    }

    @Step("Verify Passenger Info Form is displayed")
    public void verifyPassengerInfoFormIsDisplayed(){
        frmPassengerInfoForm.get().shouldBe(visible);
    }
}
