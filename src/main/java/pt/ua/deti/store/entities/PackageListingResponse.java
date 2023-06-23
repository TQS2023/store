package pt.ua.deti.store.entities;

import java.util.List;

public class PackageListingResponse {
    private final boolean success;
    private final List<PackageResponse> packages;

    public PackageListingResponse(boolean success, List<PackageResponse> packages) {
        this.success = success;
        this.packages = packages;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<PackageResponse> getPackages() {
        return packages;
    }
}
