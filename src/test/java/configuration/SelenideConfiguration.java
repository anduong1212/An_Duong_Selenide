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

    public static void loadConfig(){
        getConfig().browser(System.getProperty("selenide.browser"));
        getConfig().baseUrl(System.getProperty("selenide.browser"));
        getConfig().timeout(Long.parseLong(System.getProperty("selenide.timeout")));
        getConfig().headless(Boolean.parseBoolean(System.getProperty("selenide.headless")));
        getConfig().remote(System.getProperty("selenide.remote", ""));
        getConfig().browserSize(System.getProperty("selenide.browserSize"));
        getConfig().browserPosition(System.getProperty("selenide.browserPosition"));
        getConfig().savePageSource(Boolean.parseBoolean(System.getProperty("selenide.savePageSource")));
        getConfig().screenshots(Boolean.parseBoolean(System.getProperty("selenide.saveScreenshots")));
        getConfig().reportsFolder(System.getProperty("selenide.reportsFolder"));

    }
}
