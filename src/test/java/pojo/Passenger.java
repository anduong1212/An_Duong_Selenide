package pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Passenger {
    private Integer Adults;
    private Integer Childrens;
    private Integer Infants;

    // Constructors, Getters and Setters
    public Passenger() {
    }

    @Override
    public String toString() {
        return "PassengerData{" +
                "Adults=" + getAdults() +
                ", Childrens=" + getChildrens() +
                ", Infants=" + getInfants() +
                '}';
    }
}

