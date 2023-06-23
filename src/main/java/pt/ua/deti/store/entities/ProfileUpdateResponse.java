package pt.ua.deti.store.entities;

public class ProfileUpdateResponse {
    private final Boolean success;

    public ProfileUpdateResponse(Boolean success) {
        this.success = success;
    }

    public Boolean isSuccess() {
        return success;
    }
}
