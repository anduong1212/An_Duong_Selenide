package dataloader;

import com.fasterxml.jackson.core.type.TypeReference;
import common.JsonUtilities;
import dataobjects.BookingInformation;
import dataprovider.DataProvider;

import java.util.Map;
import java.util.stream.Stream;

public class FlightBookingData implements DataProvider<BookingInformation> {

    private static final String FILE_PATH = "data/staging_data.json";

    @Override
    public Stream<BookingInformation> provide() {
        TypeReference<Map<String, BookingInformation>> reference = new TypeReference<>() {};
        Map<String, BookingInformation> dataMap = JsonUtilities.readJsonData(FILE_PATH, reference);
        return dataMap.values().stream();
    }
}
