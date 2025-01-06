package pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightBookingData {
    private String departDestination;
    private String arrivalDestination;
    private String departDate;
    private String arrivalDate;
    private String flightType;
    private Passenger passenger;

    @Override
    public String toString() {
        return "FlightBookingData{" +
                "departDestination='" + getDepartDestination() + '\'' +
                ", arrivalDestination='" + getArrivalDestination() + '\'' +
                ", departDate='" + getDepartDate() + '\'' +
                ", arrivalDate='" + getArrivalDate() + '\'' +
                ", flightType='" + getFlightType() + '\'' +
                ", passenger=" + getPassenger() +
                '}';
    }
}
