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
}
