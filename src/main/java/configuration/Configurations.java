package configuration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideConfig;
import common.LocaleManager;
import lombok.Getter;

public class Configurations extends SelenideConfig {

    @Getter
    public static final SelenideConfig config = new SelenideConfig();


    public static void configure(String browser) {
        String localeCode = System.getProperty("config.locale");
        LocaleManager.loadLocale(localeCode);

        Configuration.baseUrl = System.getProperty("base.url");
        Configuration.browser = browser;
    }

}
