package pt.ua.deti.store.entities;


import pt.ua.deti.store.database.PickupPointEntity;
import pt.ua.deti.store.database.UserEntity;

public class UserRequest {
    private final String password;
    private final String address;
    private final String email;
    private final String creditCardNumber;
    private final Long creditCardValidity;
    private final String creditCardCVC;
    private final String preferredPickupPointId;

    public UserRequest(String password, String address, String email, String creditCardNumber, Long creditCardValidity, String creditCardCVC, String preferredPickupPointId) {
        this.password = password;
        this.address = address;
        this.email = email;
        this.creditCardNumber = creditCardNumber;
        this.creditCardValidity = creditCardValidity;
        this.creditCardCVC = creditCardCVC;
        this.preferredPickupPointId = preferredPickupPointId;
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

    public String getPreferredPickupPointId() {
        return preferredPickupPointId;
    }

    public UserEntity toEntity() {
        return new UserEntity(null, address, email, creditCardNumber, creditCardValidity, creditCardCVC, null);
    }
}
