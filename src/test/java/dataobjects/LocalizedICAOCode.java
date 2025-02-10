package dataobjects;

import java.util.Map;

public record LocalizedICAOCode(Map<String, AirportDetails> airportCode) {
    public record AirportDetails(String vi, String en) {
    }
}