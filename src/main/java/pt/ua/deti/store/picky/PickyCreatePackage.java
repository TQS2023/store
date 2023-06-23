package pt.ua.deti.store.picky;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PickyCreatePackage {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("packageId")
    private String packageId;
}
