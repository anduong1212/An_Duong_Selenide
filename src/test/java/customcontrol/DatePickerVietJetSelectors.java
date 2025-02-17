package customcontrol;

import element.control.factories.DatePickerSelector;

public record DatePickerVietJetSelectors() implements DatePickerSelector {
    @Override
    public String calendarButtonSelector() {
        return "//div//p[text()='%s']";
    }

    @Override
    public String calendarMonthYearSelector() {
        return "div.rdrMonth div.rdrMonthName";
    }

    @Override
    public String nextMonthButtonSelector() {
        return "div button.rdrNextButton";
    }

    @Override
    public String previousMonthButtonSelector() {
        return "div button.rdrPprevButton";
    }

    @Override
    public String dayCellXPathFormat() {
        return "//div[@class='rdrMonth' and contains(div,'%s')]//button[@class='rdrDay']//span[text()='%s']";
    }
}
