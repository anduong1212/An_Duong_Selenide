package pages.vietjet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class BasePage {
    private final String btnCloseBusinessAvailableAds = "//div//div/img[@alt='popup information']/ancestor::div/button";

    public void closePopupAds(){
        SelenideElement closeAdsButton = $x(btnCloseBusinessAvailableAds);
        if(closeAdsButton.shouldBe(Condition.appear).isDisplayed()){
            closeAdsButton.click();
        }
    }
}
