package agoda.customcontrol;

import element.control.factories.DatePickerSelector;

public record DatePickerAgodaSelectors() implements DatePickerSelector {
    @Override
    public String calendarButtonSelector() {
        return "";
    }

    @Override
    public String calendarMonthYearSelector() {
        return "";
    }

    @Override
    public String nextMonthButtonSelector() {
        return "";
    }

    @Override
    public String previousMonthButtonSelector() {
        return "";
    }

    @Override
    public String dayCellXPathFormat() {
        return "";
    }
}
