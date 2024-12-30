package enums;

public enum Locale {
    VN("VN_locale.properties"),
    EN("EN_locale.properties"),
    KO("KO_locale.properties");

    private final String fileName;

    Locale(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }

    public static Locale fromString(String languageCode){
        for (Locale locale : Locale.values()){
            if(locale.name().equalsIgnoreCase(languageCode)){
                return locale;
            }
        }
        throw new IllegalArgumentException("Unable to find language code " + languageCode);
    }
}
