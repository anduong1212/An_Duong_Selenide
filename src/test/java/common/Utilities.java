package common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ServiceLoader;

public class Utilities {
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    public static void sleep(long timeOut) {
        try {
            Thread.sleep(timeOut);
        } catch (Exception ex){
            Log.error("Exception is sleep method - ERROR: " + ex);
        }
    }

    public static String getDateInPrior(LocalDate date, int days){
        LocalDate futureDate = date.plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return futureDate.format(formatter);
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
