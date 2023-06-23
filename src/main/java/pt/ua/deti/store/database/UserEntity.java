package pt.ua.deti.store.database;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "app_user")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "uuid")
    private UUID userId;

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
    private PickupPointEntity preferredPickupPointId;

    public UserEntity(String password, String address, String email, String creditCardNumber, Long creditCardValidity, String creditCardCVC, PickupPointEntity preferredPickupPointId) {
        this.password = password;
        this.address = address;
        this.email = email;
        this.creditCardNumber = creditCardNumber;
        this.creditCardValidity = creditCardValidity;
        this.creditCardCVC = creditCardCVC;
        this.preferredPickupPointId = preferredPickupPointId;
    }

    public UserEntity() {
    }

    public UUID getUserId() {
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

    public PickupPointEntity getPreferredPickupPointId() {
        return preferredPickupPointId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setCreditCardValidity(Long creditCardValidity) {
        this.creditCardValidity = creditCardValidity;
    }

    public void setCreditCardCVC(String creditCardCVC) {
        this.creditCardCVC = creditCardCVC;
    }

    public void setPreferredPickupPointId(PickupPointEntity preferredPickupPointId) {
        this.preferredPickupPointId = preferredPickupPointId;
    }
}
