package testcases;

import com.codeborne.selenide.Selenide;
import configuration.SelenideConfiguration;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestBase {
    @BeforeTest
    public void setUp(){
        SelenideConfiguration.configure();
        Selenide.open();
    }

    @AfterTest
    public void cleanUp(){
        Selenide.closeWebDriver();
    }

}
