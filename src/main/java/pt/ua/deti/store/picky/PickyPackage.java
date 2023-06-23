package pt.ua.deti.store.picky;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PickyPackage {
    @JsonProperty("packageId")
    private String packageId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("items")
    private String[] items;

    @JsonProperty("address")
    private String address;

    public String getPackageId() {
        return packageId;
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
