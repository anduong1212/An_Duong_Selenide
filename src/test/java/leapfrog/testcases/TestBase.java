package leapfrog.testcases;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import static com.codeborne.selenide.Selenide.getUserAgent;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.isHeadless;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public abstract class TestBase {

    public static final String BASE_URL = "https://store.leapfrog.com/en-us/apps/c?p=%s&platforms=197&product_list_dir=asc&product_list_order=name";
    @BeforeMethod
    public void beforeMethod(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));

    }

    @BeforeTest
    public void setUp(){

    }


}
