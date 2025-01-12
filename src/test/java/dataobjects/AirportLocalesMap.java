package dataobjects;

import java.util.Map;

public record AirportLocalesMap(Map<String, AirportDetails> airportCode) {
    public record AirportDetails(String vi, String en) {
    }
}