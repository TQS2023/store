package pt.ua.deti.store.entities;


public class ProfileResponse {
    private final String password;
    private final String address;
    private final String email;
    private final String creditCardNumber;
    private final Long creditCardValidity;
    private final String creditCardCVC;
    private final PickupPointResponse preferredPickupPointId;

    public ProfileResponse(String password, String address, String email, String creditCardNumber, Long creditCardValidity, String creditCardCVC, PickupPointResponse preferredPickupPointId) {
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

    public PickupPointResponse getPreferredPickupPointId() {
        return preferredPickupPointId;
    }
}
