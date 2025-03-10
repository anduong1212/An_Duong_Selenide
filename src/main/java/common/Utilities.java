package common;

import enums.vietjet.Locales;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
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

    public static String calculateDateOfWeek(String dayOfWeekStr, String week){
        if (dayOfWeekStr == null || dayOfWeekStr.trim().isEmpty() || week == null || week.trim().isEmpty()) {
            throw new RuntimeException("Invalid input");
        }

        String normalizedDayOfWeek = dayOfWeekStr.trim().toUpperCase(Locale.ENGLISH);
        String normalizedWeek = week.trim().toLowerCase(Locale.ENGLISH);
        LocalDate currentDate = LocalDate.now();
        DayOfWeek targetDayOfWeek = null;
        LocalDate targetDate = null;

        try {
            targetDayOfWeek = DayOfWeek.valueOf(normalizedDayOfWeek);
        } catch (IllegalArgumentException e) {
           throw new RuntimeException("Invalid day of week: " + normalizedDayOfWeek);
        }

        if (normalizedWeek.equals("next week")) {
            targetDate = currentDate.with(TemporalAdjusters.next(targetDayOfWeek));
        } else if (normalizedWeek.equals("this week")) {
            targetDate = currentDate.with(TemporalAdjusters.nextOrSame(targetDayOfWeek));
        } else {
            throw new RuntimeException("Invalid week: " + normalizedWeek);
        }

        if (targetDate != null) {
            return targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            throw new RuntimeException("Error calculating date");
        }
    }

}
