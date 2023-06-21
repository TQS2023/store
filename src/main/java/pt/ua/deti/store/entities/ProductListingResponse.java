package pt.ua.deti.store.entities;

import java.util.List;

public class ProductListingResponse {
    private final List<ProductResponse> products;
    private final boolean success;

    public ProductListingResponse(List<ProductResponse> products, boolean success) {
        this.products = products;
        this.success = success;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public boolean isSuccess() {
        return success;
    }
}
