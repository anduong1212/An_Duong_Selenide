package testcases;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import configuration.Configurations;
import common.LocaleManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class TestBase {

    @BeforeTest
    @Parameters({"browser"})
    public void setUp(String browser){
        Configurations.configure(browser);

        Selenide.open(Configuration.baseUrl);
    }

    @AfterTest
    public void cleanUp(){
        Selenide.closeWebDriver();
    }

}
