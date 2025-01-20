package enums;

import common.LocaleManager;

import java.util.ResourceBundle;

public enum FlightDateTypes {
    DEPART_DATE(LocaleManager.getLocaleBundle("homepage").getString("homepage.booking.departure.button")),
    RETURN_DATE(LocaleManager.getLocaleBundle("homepage").getString("homepage.booking.return.button"));

    private final String flightDateType;

    private final LocaleManager localeManager = new LocaleManager();

    FlightDateTypes(String flightDateType){
        this.flightDateType = flightDateType;
    }

    public static FlightDateTypes fromString(String flightDateType){
        for (FlightDateTypes dateType : FlightDateTypes.values()){
            if(dateType.name().equalsIgnoreCase(flightDateType)){
                return dateType;
            }
        }
        throw new IllegalArgumentException("Unable to find flight date type " + flightDateType);
    }

    public String getFlightDateType(){
        return flightDateType;
    }
}
