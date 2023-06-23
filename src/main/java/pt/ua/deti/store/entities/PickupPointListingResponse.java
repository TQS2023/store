package pt.ua.deti.store.entities;

import java.util.List;

public class PickupPointListingResponse {
    private final List<PickupPointResponse> pickupPoints;
    private final boolean success;

    public PickupPointListingResponse(List<PickupPointResponse> pickupPoints, boolean success) {
        this.pickupPoints = pickupPoints;
        this.success = success;
    }

    public List<PickupPointResponse> getPickupPoints() {
        return pickupPoints;
    }

    public boolean isSuccess() {
        return success;
    }
}
