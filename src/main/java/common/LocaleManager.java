package common;

import enums.Locales;
import lombok.Getter;

@Getter
public class LocaleManager {
    public static PropertyUtils props = PropertyUtils.getInstance();
    public static Locales selectedLanguage;

    public static void loadLocale(String locale) {
        selectedLanguage = Locales.fromString(locale);
        props.loadPropertiesFromFile("/src/test/resources/locales/" + selectedLanguage.getLocaleCode() + "_locale.properties");


    }

    public static String getLocalizedText(String key) {
        if (props == null) {
            throw new IllegalArgumentException("You need to call loadLocale() before call this methods");
        }

        return props.getPropertyValue(key);
    }

    public static Locales getSelectedLocale() {
        return selectedLanguage != null ? selectedLanguage : Locales.EN;
    }


}
