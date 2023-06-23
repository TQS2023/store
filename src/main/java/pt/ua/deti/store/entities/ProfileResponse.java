package pt.ua.deti.store.entities;


import pt.ua.deti.store.database.UserEntity;

public class ProfileResponse {
    private final String address;
    private final String email;
    private final String creditCardNumber;
    private final Long creditCardValidity;
    private final String creditCardCVC;
    private final PickupPointResponse preferredPickupPointId;

    public ProfileResponse(String address, String email, String creditCardNumber, Long creditCardValidity, String creditCardCVC, PickupPointResponse preferredPickupPointId) {
        this.address = address;
        this.email = email;
        this.creditCardNumber = creditCardNumber;
        this.creditCardValidity = creditCardValidity;
        this.creditCardCVC = creditCardCVC;
        this.preferredPickupPointId = preferredPickupPointId;
    }

    public static ProfileResponse fromEntity(UserEntity user) {
        return new ProfileResponse(
                user.getAddress(),
                user.getEmail(),
                user.getCreditCardNumber(),
                user.getCreditCardValidity(),
                user.getCreditCardCVC(),
                PickupPointResponse.fromEntity(user.getPreferredPickupPointId())
        );
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
