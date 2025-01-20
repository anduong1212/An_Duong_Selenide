package configuration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideConfig;
import common.LocaleManager;
import lombok.Getter;

public class Configurations {

    @Getter
    public static final SelenideConfig config = new SelenideConfig();


    public static void configure() {
        String localeCode = System.getProperty("config.locale");
        LocaleManager.setLocale(localeCode);
    }

}
