package pt.ua.deti.store.picky;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PickyPickupPoint {
    @JsonProperty("pickupPointId")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
