package common;

import enums.vietjet.Locales;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    public static final Pattern DATEPLACEHOLDER_PATTERN = Pattern.compile("<TODAY([+-]?\\d*)>");
    public static final Locales selectedLocale = LocaleManager.getSelectedLocale();


    public static String resolveDatePlaceholders(String dateString) {
        Matcher matcher = DATEPLACEHOLDER_PATTERN.matcher(dateString);
        StringBuilder stringBuffer = new StringBuilder();
        while (matcher.find()) {
            String daysOffsetStr = matcher.group(1);
            int daysOffset = 0;

            if (!daysOffsetStr.isEmpty() && !daysOffsetStr.equals("+")) {
                daysOffset = Integer.parseInt(daysOffsetStr);
            }

            LocalDate localDate = LocalDate.now().plusDays(daysOffset);
            matcher.appendReplacement(stringBuffer, DateTimeFormatter.ofPattern(selectedLocale.getLocaleDateFormat(), selectedLocale.getLocale()).format(localDate));
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

}
