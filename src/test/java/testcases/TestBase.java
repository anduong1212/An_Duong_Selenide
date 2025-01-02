package testcases;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import common.PropertyUtils;
import configuration.SelenideConfiguration;
import element.LocaleManager;
import enums.Locale;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class TestBase {

    @BeforeTest
    @Parameters({"locale"})
    public void setUp(String locale){
        SelenideConfiguration.configure();
        Selenide.open(Configuration.baseUrl);
        LocaleManager.loadLocale(locale);

    }

    @AfterTest
    public void cleanUp(){
        Selenide.closeWebDriver();
    }

}
