package pt.ua.deti.store.entities;

public class CreatePackageResponse {
    private final boolean success;

    public CreatePackageResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
