package dataloader;

import com.fasterxml.jackson.core.type.TypeReference;
import common.JsonUtilities;
import common.Utilities;
import dataobjects.LocalizedICAOCode;
import dataobjects.BookingInformation;
import dataprovider.DataProvider;

import java.util.Map;
import java.util.stream.Stream;

public class FlightBookingData implements DataProvider<BookingInformation> {

    private static final String FILE_PATH = "data/staging_data.json";
    private final Map<String, LocalizedICAOCode.AirportDetails> airportDetailsMap = new AirportMapData().provide().findFirst().orElse(null);
    private final String testCaseName;

    /**
       This constructor is used to initialize the test case name
     */
    public FlightBookingData(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    @Override
    public Stream<BookingInformation> provide() {
        //Define the type reference with the BookingInformation class
        TypeReference<Map<String, BookingInformation>> reference = new TypeReference<>() {};
        Map<String, BookingInformation> dataMap = JsonUtilities.readJsonData(FILE_PATH, reference);

        if (testCaseName != null) {
            BookingInformation bookingInformation = dataMap.get(testCaseName);
            if (bookingInformation != null) {
                //Resolve the date placeholders in the booking information
                bookingInformation = resolveDateInFlightBookingData(bookingInformation);
                return Stream.of(bookingInformation);
            } else {
                throw new RuntimeException("[Booking Data Provider] Test case name not found in data file: " + testCaseName);
            }
        } else {
            //Return all the values from the data map if the test case name is not provided
            return dataMap.values().stream();
        }

    }

    private BookingInformation resolveDateInFlightBookingData(BookingInformation data) {
        return new BookingInformation(
                data.departDestination(),
                data.arrivalDestination(),
                Utilities.resolveDatePlaceholders(data.departDate()),
                Utilities.resolveDatePlaceholders(data.arrivalDate()),
                data.flightType(),
                data.passenger()
        );
    }

    private String getLocaleName(String airportCode, String locale) {
        LocalizedICAOCode.AirportDetails details = airportDetailsMap.get(airportCode);
        if (details != null){
            return "vi".equals(locale) ? details.vi() : details.en();
        }

        return airportCode;
    }
}
