package leapfrog.dataloader;

import com.fasterxml.jackson.core.type.TypeReference;
import common.JsonUtilities;
import dataprovider.DataProvider;
import leapfrog.dataobjects.TabConfig;
import leapfrog.testcases.ProductValidation;

import java.util.List;
import java.util.stream.Stream;

public class TabConfigData implements DataProvider<List<TabConfig>> {

    private final String JSON_CONFIG_PATH = "config/leapfrog/tab_config.json";
    @Override
    public Stream<List<TabConfig>> provide() {
        TypeReference<List<TabConfig>> reference = new TypeReference<>() {};
        List<TabConfig> configsList =  JsonUtilities.readJsonData(JSON_CONFIG_PATH, reference);
        return Stream.of(configsList);
    }

    public String getURLForPage(int pageNumber){
        return String.format(ProductValidation.BASE_URL, pageNumber);
    }
}
