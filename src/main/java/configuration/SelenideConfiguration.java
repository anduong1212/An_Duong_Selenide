package configuration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideConfig;
import lombok.Getter;

public class SelenideConfiguration extends SelenideConfig {

    @Getter
    public static final SelenideConfig config = new SelenideConfig();


    public static void configure(){
        Configuration.browser = config.browser();
        Configuration.baseUrl = config.baseUrl();
        Configuration.timeout = config.timeout();
        Configuration.headless = config.headless();
        Configuration.remote = config.remote();
        Configuration.browserSize = config.browserSize();
        Configuration.browserPosition = config.browserPosition();
        Configuration.reportsFolder = config.reportsFolder();
        Configuration.screenshots = config.screenshots();
    }

}
