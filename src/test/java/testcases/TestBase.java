package testcases;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.collect.ImmutableMap;
import common.LocaleManager;
import configuration.Configurations;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import static com.codeborne.selenide.Selenide.getUserAgent;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.isHeadless;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public abstract class TestBase {

    @BeforeTest
    public void setUp(){
        Configurations.configure();
        Selenide.open(Configuration.baseUrl + LocaleManager.getSelectedLocale().getLocaleCode());

    }

    @BeforeMethod
    public void beforeMethod(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));

        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", getWebDriver().getClass().getName())
                        .put("URL", Configuration.baseUrl)
                        .put("Headless", String.valueOf(isHeadless()))
                        .put("UserAgent", getUserAgent())
                        .build(), System.getProperty("user.dir") + "/target/allure-results/");
    }

    @AfterMethod
    public void tearDown(){
    }

}
