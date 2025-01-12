package dataloader;

import com.fasterxml.jackson.core.type.TypeReference;
import common.JsonUtilities;
import dataobjects.AirportLocalesMap;
import dataprovider.DataProvider;

import java.util.Map;
import java.util.stream.Stream;

public class AirportMapData implements DataProvider<Map<String, AirportLocalesMap.AirportDetails>> {
    private static final String FILE_PATH = "airport_locale_name.json";

    @Override
    public Stream<Map<String, AirportLocalesMap.AirportDetails>> provide() {
        TypeReference<Map<String, AirportLocalesMap.AirportDetails>> typeReference = new TypeReference<>() {};
        Map<String, AirportLocalesMap.AirportDetails> dataMap = JsonUtilities.readJsonData(FILE_PATH, typeReference);
        return Stream.of(dataMap);
    }
}
