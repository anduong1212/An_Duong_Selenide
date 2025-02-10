package pages.vietjet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import common.Log;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.switchTo;

public abstract class BasePage {
    private final String btnCloseBusinessAvailableAds = "//div[img[@alt='popup information']]/preceding-sibling::button";
    private final String popupAds = "//div[img[@alt='popup information']]";
    private final String panNotificationBanner = "//div[@id='st_notification_banner']";
    private final String iframeNotificationBanner = "//iframe[@id='preview-notification-frame']";
    protected final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(){
        logger.debug("Initializing Base Page for class {}", getClass().getName());
    }

    @Step("Close popup ads")
    public void closePopupAds(){
        if ($x(popupAds).shouldBe(Condition.visible, Duration.ofSeconds(Configuration.timeout)).isDisplayed()){
            $x(btnCloseBusinessAvailableAds).click();
        } else {
            Log.info("[Base Page] Ads popup banner is not displayed");
        }
    }

    @Step("Close notification banner")
    public void closeNotificationBanner(){
        if($x(panNotificationBanner).isDisplayed()){
            switchTo().frame($x(iframeNotificationBanner));

            SelenideElement btnCloseNotification = $x("//button[@id ='NC_CTA_TWO']");
            if(btnCloseNotification.exists()){
                btnCloseNotification.shouldBe(visible).click();
            }
            switchTo().defaultContent();
        } else {
            Log.info("[Base Page] Notification banner is not displayed");
        }
    }

}
