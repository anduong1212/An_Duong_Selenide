package common;

import enums.Locales;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ServiceLoader;

public class Utilities {
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    public static String getDateInPrior(int daysToAdd) {
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(daysToAdd);
        Locales selectedLocale = LocaleManager.getSelectedLocale();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(selectedLocale.getLocaleDateFormat(), selectedLocale.getLocale());
        return dateTimeFormatter.format(futureDateTime);
    }

}
