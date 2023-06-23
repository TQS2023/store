package pt.ua.deti.store.entities;


public class PackageResponse {
    private final String packageId;
    private final String userId;
    private final String status;
    private final String[] items;
    private final String address;

    public PackageResponse(String packageId, String userId, String status, String[] items, String address) {
        this.packageId = packageId;
        this.userId = userId;
        this.status = status;
        this.items = items;
        this.address = address;
    }

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
