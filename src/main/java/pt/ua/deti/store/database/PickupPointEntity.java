package pt.ua.deti.store.database;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pickup_point")
public class PickupPointEntity {
    @Id
    @Column(name = "pickup_point_id")
    private UUID pickupPointId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    public PickupPointEntity(UUID pickupPointId, String name, String address) {
        this.pickupPointId = pickupPointId;
        this.name = name;
        this.address = address;
    }

    public PickupPointEntity() {

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
