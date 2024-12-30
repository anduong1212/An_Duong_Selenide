package element;

import common.PropertyUtils;
import enums.Locale;

public class LocaleManager {
    public static PropertyUtils props = PropertyUtils.getInstance();

    public static void loadLocale(String locale){
        Locale selectedLanguage = Locale.fromString(locale);
        props.loadPropertiesFromFile("/src/test/resources/" + selectedLanguage.getFileName());
    }

    public static String getLocalizedText(String key){
        if (props == null){
            throw new IllegalArgumentException("You need to call loadLocale() before call this methods");
        }

        return props.getPropertyValue(key);
    }


}
