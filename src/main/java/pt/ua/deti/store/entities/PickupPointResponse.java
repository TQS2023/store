package pt.ua.deti.store.entities;

import pt.ua.deti.store.database.PickupPointEntity;

import java.util.UUID;

public class PickupPointResponse {
    private final String pickupPointId;
    private final String name;
    private final String address;

    public PickupPointResponse(String pickupPointId, String name, String address) {
        this.pickupPointId = pickupPointId;
        this.name = name;
        this.address = address;
    }

    public static PickupPointResponse fromEntity(PickupPointEntity preferredPickupPoint) {
        return new PickupPointResponse(preferredPickupPoint.getPickupPointId().toString(),
                preferredPickupPoint.getName(), preferredPickupPoint.getAddress());
    }

    public String getPickupPointId() {
        return pickupPointId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
