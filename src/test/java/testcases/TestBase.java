package testcases;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.collect.ImmutableMap;
import common.LocaleManager;
import configuration.Configurations;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import static com.codeborne.selenide.Selenide.getUserAgent;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.isHeadless;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class TestBase {

    @BeforeTest
    public void setUp(){
        Configurations.configure();
        Selenide.open(Configuration.baseUrl + LocaleManager.getSelectedLocale().getLocaleCode());
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @AfterMethod
    public void tearDown(){
        allureEnvironmentWriter(ImmutableMap.<String, String> builder()
                .put("BASE_URL", Configuration.baseUrl)
                .put("WebDriver", String.valueOf(getWebDriver()))
                .put("UserAgent", getUserAgent())
                .put("isHeadless", String.valueOf(isHeadless()))
                .build(), System.getProperty("user.dir") + "target/allure-results/");
        Selenide.closeWebDriver();
    }

}
