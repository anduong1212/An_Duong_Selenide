package dataloader;

import com.fasterxml.jackson.core.type.TypeReference;
import common.JsonUtilities;
import dataobjects.AirportLocalesMap;
import dataobjects.BookingInformation;
import dataprovider.DataProvider;

import java.util.Map;
import java.util.stream.Stream;

public class FlightBookingData implements DataProvider<BookingInformation> {

    private static final String FILE_PATH = "data/staging_data.json";
    private final Map<String, AirportLocalesMap.AirportDetails> airportDetailsMap = new AirportMapData().provide().findFirst().orElse(null);
    private final String testCaseName;

    /**
       This constructor is used to initialize the test case name
     */
    public FlightBookingData(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    @Override
    public Stream<BookingInformation> provide() {
        TypeReference<Map<String, BookingInformation>> reference = new TypeReference<>() {};
        Map<String, BookingInformation> dataMap = JsonUtilities.readJsonData(FILE_PATH, reference);
        if (testCaseName != null) {
            BookingInformation bookingInformation = dataMap.get(testCaseName);
            if (bookingInformation != null) {
                return Stream.of(bookingInformation);
            } else {
                throw new RuntimeException("[Booking Data Provider] Test case name not found in data file: " + testCaseName);
            }
        } else {
            return dataMap.values().stream();
        }

    }

    private String getLocaleName(String airportCode, String locale) {
        AirportLocalesMap.AirportDetails details = airportDetailsMap.get(airportCode);
        if (details != null){
            return "vi".equals(locale) ? details.vi() : details.en();
        }

        return airportCode;
    }
}
