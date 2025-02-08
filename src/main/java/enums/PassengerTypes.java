package enums;

import common.LocaleManager;

import java.util.HashMap;
import java.util.Map;

public enum PassengerTypes {
    ADULTS(LocaleManager.getLocaleBundle("homepage").getString("homepage.passenger.adults")),
    CHILDREN(LocaleManager.getLocaleBundle("homepage").getString("homepage.passenger.childrens")),
    INFANTS(LocaleManager.getLocaleBundle("homepage").getString("homepage.passenger.infants"));

    private final String passengerType;
    private static final Map<String, PassengerTypes> textToEnum = new HashMap<>();

    PassengerTypes(String passengerType){
        this.passengerType = passengerType;
    }

    public String getDisplayName(){
        return passengerType;
    }

    static {
        for (PassengerTypes type : PassengerTypes.values()){
            textToEnum.put(type.getDisplayName(), type);
        }
    }

    public static PassengerTypes fromDisplayName(String displayName){
        return textToEnum.get(displayName.substring(0,1).toUpperCase() + displayName.substring(1));
    }



}
