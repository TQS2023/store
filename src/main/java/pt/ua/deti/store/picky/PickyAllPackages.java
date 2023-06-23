package pt.ua.deti.store.picky;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PickyAllPackages {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("packages")
    private PickyPackage[] packages;
}
