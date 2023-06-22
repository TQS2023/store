package pt.ua.deti.store.database;

import jakarta.persistence.*;

@Entity
@Table(name = "pickup_point")
public class PickupPoint {
    @Id
    @Column(name = "pickup_point_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pickupPointId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
}
