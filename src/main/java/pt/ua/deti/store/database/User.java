package pt.ua.deti.store.database;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class User {
    public User(String password, String address, String email, String creditCardNumber, Long creditCardValidity, String creditCardCVC, PickupPoint preferredPickupPointId) {
        this.password = password;
        this.address = address;
        this.email = email;
        this.creditCardNumber = creditCardNumber;
        this.creditCardValidity = creditCardValidity;
        this.creditCardCVC = creditCardCVC;
        this.preferredPickupPointId = preferredPickupPointId;
    }

    public User() {
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @Column(name = "credit_card_validity")
    private Long creditCardValidity;

    @Column(name = "credit_card_cvc")
    private String creditCardCVC;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preferred_pickup_point_id", referencedColumnName = "pickup_point_id")
    private PickupPoint preferredPickupPointId;

    public Long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public Long getCreditCardValidity() {
        return creditCardValidity;
    }

    public String getCreditCardCVC() {
        return creditCardCVC;
    }

    public PickupPoint getPreferredPickupPointId() {
        return preferredPickupPointId;
    }
}
