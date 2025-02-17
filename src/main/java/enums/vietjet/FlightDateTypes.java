package enums.vietjet;

import common.LocaleManager;
import lombok.Getter;

@Getter
public enum FlightDateTypes {
    DEPART_DATE(LocaleManager.getLocaleBundle("homepage").getString("homepage.booking.departure.button")),
    RETURN_DATE(LocaleManager.getLocaleBundle("homepage").getString("homepage.booking.return.button"));

    private final String flightDateType;

    FlightDateTypes(String flightDateType) {
        this.flightDateType = flightDateType;
    }

    public static FlightDateTypes fromString(String flightDateType) {
        for (FlightDateTypes dateType : FlightDateTypes.values()) {
            if (dateType.name().equalsIgnoreCase(flightDateType)) {
                return dateType;
            }
        }
        throw new IllegalArgumentException("Unable to find flight date type " + flightDateType);
    }

}
