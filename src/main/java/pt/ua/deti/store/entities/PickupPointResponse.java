package pt.ua.deti.store.entities;

import java.util.UUID;

public class PickupPointResponse {
    private final UUID pickupPointId;
    private final String name;
    private final String address;

    public PickupPointResponse(UUID pickupPointId, String name, String address) {
        this.pickupPointId = pickupPointId;
        this.name = name;
        this.address = address;
    }

    public UUID getPickupPointId() {
        return pickupPointId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
