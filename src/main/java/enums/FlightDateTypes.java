package enums;

import common.LocaleManager;

public enum FlightDateTypes {
    DEPART_DATE(LocaleManager.getLocalizedText("homepage.booking.departure.button")),
    RETURN_DATE(LocaleManager.getLocalizedText("homepage.booking.return.button"));

    private final String flightDateType;

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
