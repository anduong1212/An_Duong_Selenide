package element.control.factories;

import com.codeborne.selenide.SelenideElement;
import element.control.DatePicker;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Logger;

public class DatePickerFactories {
    private static final Map<String, DatePickerSelector> datePickerSelectorMap = new HashMap<>();
    private static final Logger logger = Logger.getLogger(DatePickerFactories.class.getName());

    /**
     * Get the DatePickerSelector implementation for the given website
     * @param website the name of the website
     * @return DatePickerSelector implementation
     */
    static {
        ServiceLoader<DatePickerSelector> serviceLoader = ServiceLoader.load(DatePickerSelector.class);
        for (DatePickerSelector selector : serviceLoader){
            //DatePickerSelectors implementations can identify the website they are for
            String website = websiteName(selector.getClass().getSimpleName());

            datePickerSelectorMap.put(website, selector);
            logger.info("[Date Picker Factory] Put DatePickerSelector implementation for website " + website);
        } if (datePickerSelectorMap.isEmpty()){
            throw new RuntimeException("[Date Picker Factory] No DatePickerSelector implementations found");
        } else {
            logger.info("[Date Picker Factory] Found " + datePickerSelectorMap.size() + " DatePickerSelector implementations");
        }
    }

    /**
     * Create a DatePicker instance for the given website
     * @param website the name of the website
     * @return DatePicker instance
     */
    public static DatePicker createDatePicker(SelenideElement rootElement, String website){
        //Get the DatePickerSelector implementation for the given website
        DatePickerSelector selector = datePickerSelectorMap.get(website.toLowerCase());

        if (selector == null){
            throw new RuntimeException("[Date Picker Factory] Unable to find DatePickerSelector implementation for website " + website);
        }

        logger.info("[Date Picker Factory] Creating DatePicker instance for website " + website);
        return new DatePicker(rootElement, selector);
    }

    private static String websiteName(String className){
        if (className.startsWith("DatePicker") && className.endsWith("Selectors")){
            // e.g. DatePickerWebSite1Selectors -> WebSite1
            return className.substring("DatePicker".length(), className.length() - "Selectors".length()).toLowerCase();
        }
        throw new RuntimeException("[Date Picker Factory] Unable to identify website name from class name " + className);
    }
}
