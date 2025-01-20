package enums;

import java.util.Locale;

public enum Locales {
    VN("vn", new Locale("vi","VN"), "'Tháng' M,yyyy,d"),
    EN("en", Locale.ENGLISH, "MMMM,yyyy,d"),
    KO("ko  ", Locale.KOREA, "M'월',yyyy,d");

    private final String localeCode;
    private final Locale locale;
    private final String localeDateFormat;

    Locales(String localeCode, Locale locale, String localeDateFormat){
        this.localeCode = localeCode;
        this.locale = locale;
        this.localeDateFormat = localeDateFormat;
    }

    public String getLocaleCode(){
        return localeCode;
    }

    public Locale getLocale(){
        return locale;
    }

    public String getLocaleDateFormat(){
        return localeDateFormat;
    }

    public static Locales fromString(String languageCode){
        for (Locales locale : Locales.values()){
            if(locale.name().equalsIgnoreCase(languageCode)){
                return locale;
            }
        }
        throw new IllegalArgumentException("Unable to find language code " + languageCode);
    }
}
