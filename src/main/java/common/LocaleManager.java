package common;

import enums.vietjet.Locales;
import lombok.Getter;

import java.util.ResourceBundle;

@Getter
public class LocaleManager {
    public static Locales selectedLanguage;

    public static void setLocale(String locale) {
        selectedLanguage = Locales.fromString(locale);
    }


    public static Locales getSelectedLocale() {
        return selectedLanguage != null ? selectedLanguage : Locales.EN;
    }

    /**
     * Retrieves the ResourceBundle for the specified page based on the selected locale.
     *
     * This method loads the ResourceBundle for the given page using the base name
     * "locales.homepage.homepage" and the locale obtained from the {@code getSelectedLocale().getLocale()} method.
     * The ResourceBundle allows for internationalization by providing locale-specific resources.
     *
     * @param page the page for which the ResourceBundle is to be retrieved. This parameter is currently unused in the method.
     * @return the ResourceBundle corresponding to the selected locale.
     */
    public static ResourceBundle getLocaleBundle(String page){
        return ResourceBundle.getBundle("locales." + page, getSelectedLocale().getLocale());
    }




}
