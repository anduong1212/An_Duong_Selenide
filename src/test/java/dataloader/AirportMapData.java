package dataloader;

import com.fasterxml.jackson.core.type.TypeReference;
import common.JsonUtilities;
import dataobjects.LocalizedICAOCode;
import dataprovider.DataProvider;

import java.util.Map;
import java.util.stream.Stream;

public class AirportMapData implements DataProvider<Map<String, LocalizedICAOCode.AirportDetails>> {
    private static final String FILE_PATH = "data/airport_locale_name.json";

    @Override
    public Stream<Map<String, LocalizedICAOCode.AirportDetails>> provide() {
        TypeReference<Map<String, LocalizedICAOCode.AirportDetails>> typeReference = new TypeReference<>() {};
        Map<String, LocalizedICAOCode.AirportDetails> dataMap = JsonUtilities.readJsonData(FILE_PATH, typeReference);
        return Stream.of(dataMap);
    }
}
