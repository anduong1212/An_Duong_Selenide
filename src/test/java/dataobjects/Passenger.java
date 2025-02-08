package dataobjects;

import java.util.Map;

public record Passenger(int adults, int childrens, int infants) {
    public Map<String, Integer> toMap() {
        return Map.of(
                "adults", adults,
                "childrens", childrens,
                "infants", infants
        );
    }
}
