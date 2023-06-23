package pt.ua.deti.store.picky;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PickyPickupPoints {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("pickupPoints")
    private PickyPickupPoint[] pickupPoints;

    public boolean isSuccess() {
        return success;
    }

    public PickyPickupPoint[] getPickupPoints() {
        return pickupPoints;
    }
}
