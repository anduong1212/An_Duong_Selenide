package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class Airport {
    private Map<String, Map<String, String>> airports;

    public Airport() {
    }

}
