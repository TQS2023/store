package pt.ua.deti.store.picky;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PickyPackageRequest {
    private final String userId;
    private final String status;
    private final String[] items;
    private final String address;

    public PickyPackageRequest(String userId, String status, String[] items, String address) {
        this.userId = userId;
        this.status = status;
        this.items = items;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String[] getItems() {
        return items;
    }

    public String getAddress() {
        return address;
    }
}
