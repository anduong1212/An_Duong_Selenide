package common;

import enums.Locales;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ServiceLoader;

public class Utilities {
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    public static String getDateInPrior(int daysToAdd){
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(daysToAdd);
        Locales selectedLocale = LocaleManager.getSelectedLocale();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(selectedLocale.getLocaleDateFormat(), selectedLocale.getLocale());
        return dateTimeFormatter.format(futureDateTime);
    }

    public static <T> T getService(Class<T> serviceInterface, String serviceName){
        return ServiceLoader.load(serviceInterface)
                .stream()
                .filter(provider -> provider.type().getSimpleName().toLowerCase()
                        .startsWith(serviceName.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Unsupported service: %s\nPlease add a service provider to META-INF/services/%s",
                                serviceName, serviceInterface.getName())))
                .get();

    }
}
