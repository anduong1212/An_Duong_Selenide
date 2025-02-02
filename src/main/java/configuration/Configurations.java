package configuration;

import common.LocaleManager;

public class Configurations {

    public static void configure() {
        String localeCode = System.getProperty("config.locale");
        LocaleManager.setLocale(localeCode);
    }

}
