package pages.vietjet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class BasePage {
    private final String btnCloseBusinessAvailableAds = "//div[img[@alt='popup information']]/preceding-sibling::button";
    private final String popupAds = "//div[img[@alt='popup information']]";
    public void closePopupAds(){
        if ($x(popupAds).shouldBe(Condition.visible, Duration.ofSeconds(Configuration.timeout)).isDisplayed()){
            $x(btnCloseBusinessAvailableAds).click();
        }
    }
}
