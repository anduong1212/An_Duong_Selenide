package leapfrog.testcases;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public abstract class TestBase {
    @BeforeMethod
    public void beforeMethod(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @BeforeTest
    public void setUp(){
        Selenide.open("https://store.leapfrog.com/en-us/apps/c?p=1&platforms=197&product_list_dir=asc&product_list_order=name");
    }


}
