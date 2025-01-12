package dataobjects;

public record BookingInformation(
        String departDestination,
        String arrivalDestination,
        String departDate,
        String arrivalDate,
        String flightType,
        Passenger passenger) {
}
